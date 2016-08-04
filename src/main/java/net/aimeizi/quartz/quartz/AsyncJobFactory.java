package net.aimeizi.quartz.quartz;

import net.aimeizi.quartz.model.ScheduleJob;
import net.aimeizi.quartz.vo.ScheduleJobVo;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 异步任务工厂
 * version : 1.0
 */
public class AsyncJobFactory extends QuartzJobBean {

    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(AsyncJobFactory.class);
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("AsyncJobFactory execute");
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJobVo.JOB_PARAM_KEY);
        System.out.println("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);
    }
}
