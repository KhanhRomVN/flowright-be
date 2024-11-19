package com.flowright.task_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.flowright.task_service.dto.MiniTaskDTO.CreateMiniTaskRequest;
import com.flowright.task_service.dto.MiniTaskDTO.GetMiniTaskResponse;
import com.flowright.task_service.dto.TaskAssignmentDTO.CreateTaskAssignmentRequest;
import com.flowright.task_service.dto.TaskAssignmentDTO.GetTaskAssignmentResponse;
import com.flowright.task_service.dto.TaskCommentDTO.GetTaskCommentResponse;
import com.flowright.task_service.dto.TaskDTO.CreateTaskRequest;
import com.flowright.task_service.dto.TaskDTO.CreateTaskResponse;
import com.flowright.task_service.dto.TaskDTO.GetAllTaskProjectResponse;
import com.flowright.task_service.dto.TaskDTO.GetAllTaskTeamListResponse;
import com.flowright.task_service.dto.TaskDTO.GetAllTaskTeamResponse;
import com.flowright.task_service.dto.TaskDTO.GetAllTaskWorkspaceResponse;
import com.flowright.task_service.dto.TaskDTO.GetMemberStatusTaskResponse;
import com.flowright.task_service.dto.TaskDTO.GetMemberTaskResponse;
import com.flowright.task_service.dto.TaskDTO.GetTaskResponse;
import com.flowright.task_service.dto.TaskDTO.GetTaskWorkspaceResponse;
import com.flowright.task_service.dto.TaskDTO.UpdateTaskResponse;
import com.flowright.task_service.dto.TaskLinkDTO.CreateTaskLinkRequest;
import com.flowright.task_service.dto.TaskLinkDTO.GetTaskLinkResponse;
import com.flowright.task_service.dto.TaskLogDTO.GetTaskLogResponse;
import com.flowright.task_service.entity.MiniTask;
import com.flowright.task_service.entity.Task;
import com.flowright.task_service.entity.TaskAssignment;
import com.flowright.task_service.entity.TaskComment;
import com.flowright.task_service.entity.TaskGroup;
import com.flowright.task_service.entity.TaskLink;
import com.flowright.task_service.entity.TaskLog;
import com.flowright.task_service.exception.TaskException;
import com.flowright.task_service.kafka.consumer.GetMemberInfoConsumer;
import com.flowright.task_service.kafka.consumer.GetMemberInfoTimerConsumer;
import com.flowright.task_service.kafka.consumer.GetProjectInfoConsumer;
import com.flowright.task_service.kafka.consumer.GetTeamInfoConsumer;
import com.flowright.task_service.kafka.consumer.GetUserInfoConsumer;
import com.flowright.task_service.kafka.producer.GetMemberInfoProducer;
import com.flowright.task_service.kafka.producer.GetMemberInfoTimerProducer;
import com.flowright.task_service.kafka.producer.GetProjectInfoProducer;
import com.flowright.task_service.kafka.producer.GetTeamInfoProducer;
import com.flowright.task_service.kafka.producer.GetUserInfoProducer;
import com.flowright.task_service.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final TaskAssignmentService taskAssignmentService;

    @Autowired
    private final TaskLinkService taskLinkService;

    @Autowired
    private final MiniTaskService miniTaskService;

    @Autowired
    private final GetUserInfoProducer getUserInfoProducer;

    @Autowired
    private final GetUserInfoConsumer getUserInfoConsumer;

    @Autowired
    private final GetProjectInfoProducer getProjectInfoProducer;

    @Autowired
    private final GetProjectInfoConsumer getProjectInfoConsumer;

    @Autowired
    private final TaskGroupService taskGroupService;

    @Autowired
    private final GetMemberInfoProducer getMemberInfoProducer;

    @Autowired
    private final GetMemberInfoConsumer getMemberInfoConsumer;

    @Autowired
    private final TaskCommentService taskCommentService;

    @Autowired
    private final TaskLogService taskLogService;

    @Autowired
    private final GetTeamInfoProducer getTeamInfoProducer;

    @Autowired
    private final GetTeamInfoConsumer getTeamInfoConsumer;

    @Autowired
    private final GetMemberInfoTimerProducer getMemberInfoTimerProducer;

    @Autowired
    private final GetMemberInfoTimerConsumer getMemberInfoTimerConsumer;

    private static final String TASK_CACHE_PREFIX = "task:";
    private static final String TASK_LIST_CACHE_PREFIX = "task-list:";

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    private String getTaskKey(UUID taskId) {
        return TASK_CACHE_PREFIX + taskId.toString();
    }

    private String getTaskListKey(UUID projectId) {
        return TASK_LIST_CACHE_PREFIX + projectId.toString();
    }

    public CreateTaskResponse createTask(CreateTaskRequest request, UUID creatorId) {
        String startDate = null;
        String endDate = null;
        if (request.getStartDate() != null) {
            startDate = request.getStartDate();
        }
        if (request.getEndDate() != null) {
            endDate = request.getEndDate();
        }
        Task task = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .creatorId(creatorId)
                .projectId(UUID.fromString(request.getProjectId()))
                .teamId(UUID.fromString(request.getTeamId()))
                .priority(request.getPriority())
                .startDate(startDate != null ? LocalDateTime.parse(startDate) : null)
                .endDate(endDate != null ? LocalDateTime.parse(endDate) : null)
                .nextTaskId(request.getNextTaskId() != null ? UUID.fromString(request.getNextTaskId()) : null)
                .taskGroupId(request.getTaskGroupId() != null ? UUID.fromString(request.getTaskGroupId()) : null)
                .status("todo")
                .previousTaskId(
                        request.getPreviousTaskId() != null ? UUID.fromString(request.getPreviousTaskId()) : null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Task savedTask = taskRepository.save(task);

        if (request.getTaskAssignments() != null) {
            for (CreateTaskAssignmentRequest taskAssignment : request.getTaskAssignments()) {
                taskAssignmentService.createTaskAssignment(
                        savedTask.getId(),
                        UUID.fromString(taskAssignment.getMemberId()),
                        UUID.fromString(taskAssignment.getTeamId()));
            }
        }

        if (request.getTaskLinks() != null) {
            for (CreateTaskLinkRequest taskLink : request.getTaskLinks()) {
                taskLinkService.createTaskLink(savedTask.getId(), taskLink.getTitle(), taskLink.getLink());
            }
        }

        if (request.getMiniTasks() != null) {
            for (CreateMiniTaskRequest miniTask : request.getMiniTasks()) {
                miniTaskService.createMiniTask(
                        savedTask.getId(),
                        miniTask.getName(),
                        miniTask.getDescription(),
                        "todo",
                        UUID.fromString(miniTask.getMemberId()));
            }
        }

        taskLogService.createTaskLog(savedTask.getId(), "Task created", "Task created successfully");

        return CreateTaskResponse.builder().message("Task created successfully").build();
    }

    public GetAllTaskWorkspaceResponse getAllTaskWorkspace(String token) {
        List<GetTaskWorkspaceResponse> tasks = taskRepository.findAll().stream()
                .map(task -> GetTaskWorkspaceResponse.builder()
                        .taskId(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .priority(task.getPriority())
                        .build())
                .collect(Collectors.toList());
        return GetAllTaskWorkspaceResponse.builder().tasks(tasks).build();
    }

    public Task getTaskById(UUID taskId) {
        return taskRepository.findById(taskId).get();
    }

    public GetAllTaskTeamListResponse getAllTaskTeam(UUID teamId) {
        List<UUID> taskIds = taskAssignmentService.getAllTaskAssignmentTeamId(teamId);
        List<GetAllTaskTeamResponse> tasks = new ArrayList<>();
        // taskId, taskName, taskDescription, priority, creatorId, projectId,
        // taskGroupId , nextTaskId, previousTaskId,
        // startDate, endDate, status
        for (UUID taskId : taskIds) {
            Task task = taskRepository.findById(taskId).get();
            tasks.add(GetAllTaskTeamResponse.builder()
                    .taskId(task.getId())
                    .taskName(task.getName())
                    .taskDescription(task.getDescription())
                    .priority(task.getPriority())
                    .creatorId(task.getCreatorId())
                    .projectId(task.getProjectId())
                    .taskGroupId(task.getTaskGroupId())
                    .nextTaskId(task.getNextTaskId())
                    .previousTaskId(task.getPreviousTaskId())
                    .startDate(task.getStartDate())
                    .endDate(task.getEndDate())
                    .status(task.getStatus())
                    .taskAssignments(new ArrayList<>())
                    .build());
        }

        // creatorUsername, projectName, taskGroupName, nextTaskName, previousTaskName
        for (GetAllTaskTeamResponse task : tasks) {
            // get creator username
            getUserInfoProducer.sendMessage(task.getCreatorId());
            String getUserInfoConsumerResponse = getUserInfoConsumer.getResponse();
            String[] responseSplit = getUserInfoConsumerResponse.split(",");
            task.setCreatorUsername(responseSplit[0]);
            // get project name
            getProjectInfoProducer.sendMessage(task.getProjectId());
            String getProjectInfoConsumerResponse = getProjectInfoConsumer.getResponse();
            String[] projectResponseSplit = getProjectInfoConsumerResponse.split(",");
            task.setProjectName(projectResponseSplit[0]);
            // get task group name
            if (task.getTaskGroupId() != null) {
                TaskGroup taskGroup = taskGroupService.getTaskGroupById(task.getTaskGroupId());
                task.setTaskGroupName(taskGroup.getName());
            }
            // get next task name
            if (task.getNextTaskId() != null) {
                Task nextTask = getTaskById(task.getNextTaskId());
                task.setNextTaskName(nextTask.getName());
            }
            // get previous task name
            if (task.getPreviousTaskId() != null) {
                Task previousTask = getTaskById(task.getPreviousTaskId());
                task.setPreviousTaskName(previousTask.getName());
            }

            // get task assignment
            List<TaskAssignment> taskAssignments = taskAssignmentService.getAllTaskAssignmentByTaskId(task.getTaskId());
            for (TaskAssignment taskAssignment : taskAssignments) {
                getUserInfoProducer.sendMessage(taskAssignment.getMemberId());
                String _getUserInfoConsumerResponse = getUserInfoConsumer.getResponse();
                String[] _responseSplit = _getUserInfoConsumerResponse.split(",");
                String assigneeUsername = _responseSplit[0];
                task.getTaskAssignments()
                        .add(GetTaskAssignmentResponse.builder()
                                .assignmentMemberId(taskAssignment.getMemberId())
                                .assigneeUsername(assigneeUsername)
                                .build());
            }
        }
        return GetAllTaskTeamListResponse.builder().tasks(tasks).build();
    }

    public GetTaskResponse getTaskByTaskId(UUID taskId) {
        Task task = getTaskById(taskId);
        List<GetTaskAssignmentResponse> taskAssignmentResponses = new ArrayList<>();
        List<GetTaskLinkResponse> taskLinkResponses = new ArrayList<>();
        List<GetTaskCommentResponse> taskCommentResponses = new ArrayList<>();
        List<GetTaskLogResponse> taskLogResponses = new ArrayList<>();
        List<GetMiniTaskResponse> miniTaskResponses = new ArrayList<>();
        List<TaskAssignment> taskAssignments = taskAssignmentService.getAllTaskAssignmentByTaskId(taskId);
        List<TaskLink> taskLinks = taskLinkService.getAllTaskLinksByTaskId(taskId);
        List<TaskComment> taskComments = taskCommentService.getAllTaskCommentsByTaskId(taskId);
        List<TaskLog> taskLogs = taskLogService.getAllTaskLogsByTaskId(taskId);
        List<MiniTask> miniTasks = miniTaskService.getAllMiniTasksByTaskId(taskId);

        // taskAssignmentResponses
        if (taskAssignments != null) {
            for (TaskAssignment taskAssignment : taskAssignments) {
                String memberInfo = getMemberInfoFromCache(taskAssignment.getMemberId());
                String[] responseSplit = memberInfo.split(",");
                String assigneeUsername = responseSplit[0];
                String assigneeEmail = responseSplit[1];

                taskAssignmentResponses.add(GetTaskAssignmentResponse.builder()
                        .assignmentId(taskAssignment.getId())
                        .assignmentMemberId(taskAssignment.getMemberId())
                        .assigneeUsername(assigneeUsername)
                        .assigneeEmail(assigneeEmail)
                        .build());
            }
        }

        // taskLinkResponses
        if (taskLinks != null) {
            for (TaskLink taskLink : taskLinks) {
                taskLinkResponses.add(GetTaskLinkResponse.builder()
                        .taskLinkId(taskLink.getId())
                        .taskId(taskLink.getTaskId())
                        .title(taskLink.getTitle())
                        .link(taskLink.getLink())
                        .build());
            }
        }

        // taskCommentResponses
        if (taskComments != null) {
            for (TaskComment taskComment : taskComments) {
                getMemberInfoProducer.sendMessage(taskComment.getMemberId());
                String getMemberInfoConsumerResponse = getMemberInfoConsumer.getResponse();
                String[] responseSplit = getMemberInfoConsumerResponse.split(",");
                String memberUsername = responseSplit[0];
                String memberEmail = responseSplit[1];
                taskCommentResponses.add(GetTaskCommentResponse.builder()
                        .commentId(taskComment.getId())
                        .taskId(taskComment.getTaskId())
                        .memberId(taskComment.getMemberId())
                        .memberUsername(memberUsername)
                        .memberEmail(memberEmail)
                        .comment(taskComment.getComment())
                        .createdAt(taskComment.getCreatedAt())
                        .build());
            }
        }

        // taskLogResponses
        if (taskLogs != null) {
            for (TaskLog taskLog : taskLogs) {
                taskLogResponses.add(GetTaskLogResponse.builder()
                        .taskLogId(taskLog.getId())
                        .taskLogTitle(taskLog.getLogTitle())
                        .taskLogDescription(taskLog.getLogDescription())
                        .taskLogDate(taskLog.getLogDate())
                        .build());
            }
        }

        // miniTaskResponses
        if (miniTasks != null) {
            for (MiniTask miniTask : miniTasks) {
                String memberInfo = getMemberInfoFromCache(miniTask.getMemberId());
                String[] responseSplit = memberInfo.split(",");
                String memberUsername = responseSplit[0];
                String memberEmail = responseSplit[1];

                miniTaskResponses.add(GetMiniTaskResponse.builder()
                        .miniTaskId(miniTask.getId())
                        .taskId(miniTask.getTaskId())
                        .miniTaskName(miniTask.getName())
                        .miniTaskDescription(miniTask.getDescription())
                        .miniTaskStatus(miniTask.getStatus())
                        .miniTaskMemberId(miniTask.getMemberId())
                        .miniTaskMemberUsername(memberUsername)
                        .miniTaskMemberEmail(memberEmail)
                        .build());
            }
        }

        // get creator info
        getUserInfoProducer.sendMessage(task.getCreatorId());
        String getUserInfoConsumerResponse = getUserInfoConsumer.getResponse();
        String[] responseSplit = getUserInfoConsumerResponse.split(",");
        String creatorUsername = responseSplit[0];
        String creatorEmail = responseSplit[1];

        // get project info
        getProjectInfoProducer.sendMessage(task.getProjectId());
        String getProjectInfoConsumerResponse = getProjectInfoConsumer.getResponse();
        String[] projectResponseSplit = getProjectInfoConsumerResponse.split(",");
        String projectName = projectResponseSplit[0];

        // get task group info
        String taskGroupName = null;
        if (task.getTaskGroupId() != null) {
            TaskGroup taskGroup = taskGroupService.getTaskGroupById(task.getTaskGroupId());
            taskGroupName = taskGroup.getName();
        }

        // get next task info
        String nextTaskName = null;
        if (task.getNextTaskId() != null) {
            Task nextTask = getTaskById(task.getNextTaskId());
            nextTaskName = nextTask.getName();
        }

        // get previous task info
        String previousTaskName = null;
        if (task.getPreviousTaskId() != null) {
            Task previousTask = getTaskById(task.getPreviousTaskId());
            previousTaskName = previousTask.getName();
        }

        // get team info
        getTeamInfoProducer.sendMessage(task.getTeamId());
        String getTeamInfoConsumerResponse = getTeamInfoConsumer.getResponse();

        return GetTaskResponse.builder()
                .taskId(task.getId())
                .teamId(task.getTeamId())
                .teamName(getTeamInfoConsumerResponse)
                .taskName(task.getName())
                .taskDescription(task.getDescription())
                .priority(task.getPriority())
                .creatorId(task.getCreatorId())
                .creatorUsername(creatorUsername)
                .creatorEmail(creatorEmail)
                .projectId(task.getProjectId())
                .projectName(projectName)
                .taskGroupId(task.getTaskGroupId())
                .taskGroupName(taskGroupName)
                .nextTaskId(task.getNextTaskId())
                .nextTaskName(nextTaskName)
                .previousTaskId(task.getPreviousTaskId())
                .previousTaskName(previousTaskName)
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .status(task.getStatus())
                .taskAssignments(taskAssignmentResponses)
                .taskLinks(taskLinkResponses)
                .taskComments(taskCommentResponses)
                .taskLogs(taskLogResponses)
                .miniTasks(miniTaskResponses)
                .build();
    }

    private String getMemberInfoFromCache(UUID memberId) {
        String memberKey = "member:" + memberId.toString();
        String memberInfo = (String) redisTemplate.opsForValue().get(memberKey);

        if (memberInfo == null) {
            try {
                getMemberInfoTimerProducer.sendMessage(memberId);
                String response =
                        getMemberInfoTimerConsumer.waitForResponse(memberId).get(2, TimeUnit.SECONDS);

                // Cache the response with 1 hour expiration
                redisTemplate.opsForValue().set(memberKey, response, 1, TimeUnit.HOURS);
                return response;
            } catch (Exception e) {
                throw new TaskException("Failed to get member information", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return memberInfo;
    }

    public UpdateTaskResponse updateTaskName(String name, UUID taskId) {
        Task task = getTaskById(taskId);
        task.setName(name);
        taskRepository.save(task);
        taskLogService.createTaskLog(taskId, "Task name updated", "Task name updated successfully");
        return UpdateTaskResponse.builder()
                .message("Task name updated successfully")
                .build();
    }

    public UpdateTaskResponse updateTaskDescription(String description, UUID taskId) {
        Task task = getTaskById(taskId);
        task.setDescription(description);
        taskRepository.save(task);
        taskLogService.createTaskLog(taskId, "Task description updated", "Task description updated successfully");
        return UpdateTaskResponse.builder()
                .message("Task description updated successfully")
                .build();
    }

    public UpdateTaskResponse updateTaskPriority(String priority, UUID taskId) {
        Task task = getTaskById(taskId);
        task.setPriority(priority);
        taskRepository.save(task);
        taskLogService.createTaskLog(taskId, "Task priority updated", "Task priority updated successfully");
        return UpdateTaskResponse.builder()
                .message("Task priority updated successfully")
                .build();
    }

    public UpdateTaskResponse updateTaskStatus(String status, UUID taskId) {
        Task task = getTaskById(taskId);
        if (task.getStatus().equals(status)) {
            return UpdateTaskResponse.builder()
                    .message("Task status is already " + status)
                    .build();
        }

        String finalStatus = "";
        if (status.equals("done")) {
            if (task.getStatus().equals("overdue")) {
                finalStatus = "overdone";
            } else {
                finalStatus = "done";
            }
        } else if (status.equals("cancelled")) {
            finalStatus = "cancel";
        } else {
            finalStatus = status;
        }

        task.setStatus(finalStatus);
        taskRepository.save(task);
        taskLogService.createTaskLog(taskId, "Task status updated", "Task status updated successfully");
        return UpdateTaskResponse.builder()
                .message("Task status updated successfully")
                .build();
    }

    public UpdateTaskResponse updateTaskEndDate(LocalDateTime endDate, UUID taskId) {
        Task task = getTaskById(taskId);
        task.setEndDate(endDate);
        taskRepository.save(task);
        taskLogService.createTaskLog(taskId, "Task end date updated", "Task end date updated successfully");
        return UpdateTaskResponse.builder()
                .message("Task end date updated successfully")
                .build();
    }

    public List<GetAllTaskProjectResponse> getAllTaskProject(UUID projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        List<GetAllTaskProjectResponse> response = new ArrayList<>();
        for (Task task : tasks) {
            // get task assignments
            List<TaskAssignment> taskAssignments = taskAssignmentService.getAllTaskAssignmentByTaskId(task.getId());
            List<GetTaskAssignmentResponse> taskAssignmentResponses = new ArrayList<>();
            for (TaskAssignment taskAssignment : taskAssignments) {
                getMemberInfoProducer.sendMessage(taskAssignment.getMemberId());
                String _getMemberInfoConsumerResponse = getMemberInfoConsumer.getResponse();
                String[] _responseSplit = _getMemberInfoConsumerResponse.split(",");
                String assigneeUsername = _responseSplit[0];
                taskAssignmentResponses.add(GetTaskAssignmentResponse.builder()
                        .assignmentMemberId(taskAssignment.getMemberId())
                        .assigneeUsername(assigneeUsername)
                        .build());
            }
            // get task group name
            String taskGroupName = null;
            if (task.getTaskGroupId() != null) {
                TaskGroup taskGroup = taskGroupService.getTaskGroupById(task.getTaskGroupId());
                taskGroupName = taskGroup.getName();
            }
            response.add(GetAllTaskProjectResponse.builder()
                    .taskId(task.getId())
                    .taskName(task.getName())
                    .priority(task.getPriority())
                    .projectId(task.getProjectId())
                    .taskGroupId(task.getTaskGroupId())
                    .taskGroupName(taskGroupName)
                    .startDate(task.getStartDate())
                    .endDate(task.getEndDate())
                    .status(task.getStatus())
                    .taskAssignments(taskAssignmentResponses)
                    .build());
        }
        return response;
    }

    public UpdateTaskResponse changeTaskGroup(UUID taskId, UUID taskGroupId) {
        Task task = getTaskById(taskId);
        task.setTaskGroupId(taskGroupId);
        taskRepository.save(task);
        return UpdateTaskResponse.builder()
                .message("Task group updated successfully")
                .build();
    }

    public List<GetMemberTaskResponse> getAllTaskMember(UUID memberId) {
        List<UUID> taskIds = taskAssignmentService.getAllTaskAssignmentMemberId(memberId);
        List<GetMemberTaskResponse> response = new ArrayList<>();
        for (UUID taskId : taskIds) {
            Task task = getTaskById(taskId);
            getProjectInfoProducer.sendMessage(task.getProjectId());
            String getProjectInfoConsumerResponse = getProjectInfoConsumer.getResponse();
            String[] projectResponseSplit = getProjectInfoConsumerResponse.split(",");
            String projectName = projectResponseSplit[0];
            getTeamInfoProducer.sendMessage(task.getTeamId());
            String getTeamInfoConsumerResponse = getTeamInfoConsumer.getResponse();
            response.add(GetMemberTaskResponse.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .description(task.getDescription())
                    .status(task.getStatus())
                    .priority(task.getPriority())
                    .projectId(task.getProjectId())
                    .projectName(projectName)
                    .startDate(task.getStartDate())
                    .endDate(task.getEndDate())
                    .teamId(task.getTeamId())
                    .teamName(getTeamInfoConsumerResponse)
                    .build());
        }
        return response;
    }

    @Scheduled(fixedRate = 10000) // 1 minute
    public void changeStatusTaskSchedule() {
        LocalDateTime now = LocalDateTime.now();
        // Get all tasks with status "todo" or "in_progress"
        List<Task> tasks = taskRepository.findAll().stream()
                .filter(task ->
                        task.getStatus().equals("todo") || task.getStatus().equals("in_progress"))
                .collect(Collectors.toList());

        for (Task task : tasks) {
            String currentStatus = task.getStatus();
            LocalDateTime startDate = task.getStartDate();
            LocalDateTime endDate = task.getEndDate();

            if (startDate == null || endDate == null) {
                continue; // Skip tasks without dates
            }

            if (currentStatus.equals("todo")) {
                // Check if current time is between start and end date
                if (now.isAfter(startDate) && now.isBefore(endDate)) {
                    task.setStatus("in_progress");
                    taskRepository.save(task);
                    taskLogService.createTaskLog(
                            task.getId(),
                            "Task status updated automatically",
                            "Task status changed from 'todo' to 'in_progress'");
                }
            } else if (currentStatus.equals("in_progress")) {
                // Check if current time is after end date
                if (now.isAfter(endDate)) {
                    task.setStatus("overdue");
                    taskRepository.save(task);
                    taskLogService.createTaskLog(
                            task.getId(),
                            "Task status updated automatically",
                            "Task status changed from 'in_progress' to 'overdue'");
                }
            }
        }
    }

    public List<GetMemberStatusTaskResponse> getAllTaskMemberWithStatus(UUID memberId) {
        List<UUID> taskIds = taskAssignmentService.getAllTaskAssignmentMemberId(memberId);
        List<GetMemberStatusTaskResponse> response = new ArrayList<>();
        for (UUID taskId : taskIds) {
            Task task = getTaskById(taskId);
            if (task.getStatus().equals("todo")
                    || task.getStatus().equals("in_progress")
                    || task.getStatus().equals("overdue")) {

                getProjectInfoProducer.sendMessage(task.getProjectId());
                String getProjectInfoConsumerResponse = getProjectInfoConsumer.getResponse();
                String[] projectResponseSplit = getProjectInfoConsumerResponse.split(",");
                String projectName = projectResponseSplit[0];
                getTeamInfoProducer.sendMessage(task.getTeamId());
                String getTeamInfoConsumerResponse = getTeamInfoConsumer.getResponse();
                // get task assignments
                List<TaskAssignment> taskAssignments = taskAssignmentService.getAllTaskAssignmentByTaskId(task.getId());
                List<GetTaskAssignmentResponse> taskAssignmentResponses = new ArrayList<>();
                for (TaskAssignment taskAssignment : taskAssignments) {
                    getMemberInfoProducer.sendMessage(taskAssignment.getMemberId());
                    String getMemberInfoConsumerResponse = getMemberInfoConsumer.getResponse();
                    String[] responseSplit = getMemberInfoConsumerResponse.split(",");
                    String assigneeUsername = responseSplit[0];
                    taskAssignmentResponses.add(GetTaskAssignmentResponse.builder()
                            .assignmentId(taskAssignment.getId())
                            .assignmentMemberId(taskAssignment.getMemberId())
                            .assigneeUsername(assigneeUsername)
                            .assigneeEmail(responseSplit[1])
                            .build());
                }
                // get mini tasks
                List<MiniTask> miniTasks = miniTaskService.getAllMiniTasksByTaskId(task.getId());
                List<GetMiniTaskResponse> miniTaskResponses = new ArrayList<>();
                for (MiniTask miniTask : miniTasks) {
                    getMemberInfoProducer.sendMessage(miniTask.getMemberId());
                    String getMemberInfoConsumerResponse = getMemberInfoConsumer.getResponse();
                    String[] responseSplit = getMemberInfoConsumerResponse.split(",");
                    String assigneeUsername = responseSplit[0];
                    miniTaskResponses.add(GetMiniTaskResponse.builder()
                            .miniTaskId(miniTask.getId())
                            .miniTaskName(miniTask.getName())
                            .miniTaskDescription(miniTask.getDescription())
                            .miniTaskStatus(miniTask.getStatus())
                            .miniTaskMemberId(miniTask.getMemberId())
                            .miniTaskMemberUsername(assigneeUsername)
                            .miniTaskMemberEmail(responseSplit[1])
                            .taskId(miniTask.getTaskId())
                            .build());
                }

                response.add(GetMemberStatusTaskResponse.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .priority(task.getPriority())
                        .projectId(task.getProjectId())
                        .projectName(projectName)
                        .startDate(task.getStartDate())
                        .endDate(task.getEndDate())
                        .teamId(task.getTeamId())
                        .teamName(getTeamInfoConsumerResponse)
                        .taskAssignments(taskAssignmentResponses)
                        .miniTasks(miniTaskResponses)
                        .build());
            }
        }
        return response;
    }
}
