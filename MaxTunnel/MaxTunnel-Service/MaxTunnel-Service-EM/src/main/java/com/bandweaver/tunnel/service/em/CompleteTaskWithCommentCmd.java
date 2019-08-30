package com.bandweaver.tunnel.service.em;

import com.bandweaver.tunnel.common.platform.log.LogUtil;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.task.IdentityLinkType;

import java.util.Map;

public class CompleteTaskWithCommentCmd implements Command<Object> {
    private String taskId;
    private String comment;
    private Map<String, Object> variables;

    public CompleteTaskWithCommentCmd(String taskId, Map<String, Object> variables, String comment) {
        this.taskId = taskId;
        this.variables = variables;
        this.comment = comment;
    }

    @Override
    public Object execute(CommandContext commandContext) {
        LogUtil.info("commandContext: " + commandContext);
        TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
        LogUtil.info("taskEntityManager: " + taskEntityManager);
        LogUtil.info("taskId: " + taskId);
        TaskEntity taskEntity = taskEntityManager.findTaskById(taskId);
        LogUtil.info("taskEntity: " + taskEntity);

        if (variables != null) {
            taskEntity.setExecutionVariables(variables);

            LogUtil.info("execute variables : " + variables.toString());
        }

        taskEntity.fireEvent(TaskListener.EVENTNAME_COMPLETE);

        if ((Authentication.getAuthenticatedUserId() != null) && (taskEntity.getProcessInstanceId() != null)) {
            taskEntity.getProcessInstance().involveUser(Authentication.getAuthenticatedUserId(), IdentityLinkType.PARTICIPANT);
        }

        commandContext.getTaskEntityManager().deleteTask(taskEntity, comment, false);

        if (taskEntity.getExecutionId() != null) {
            ExecutionEntity execution = taskEntity.getExecution();
            execution.removeTask(taskEntity);
            execution.signal(null, null);
        }

        return null;
    }
}
