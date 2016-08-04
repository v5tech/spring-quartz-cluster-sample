package net.aimeizi.quartz.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.aimeizi.quartz.service.ScheduleJobService;
import net.aimeizi.quartz.vo.ScheduleJobVo;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 定时任务控制器
 * version : 1.0
 */
@Controller
public class ScheduleJobController {

    /** job service */
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 任务页面
     *
     * @return
     */
    @RequestMapping(value = "input-schedule-job", method = RequestMethod.GET)
    public String inputScheduleJob(ScheduleJobVo scheduleJobVo, ModelMap modelMap) {

        if (scheduleJobVo.getScheduleJobId() != null) {
            ScheduleJobVo scheduleJob = scheduleJobService.get(scheduleJobVo.getScheduleJobId());
            scheduleJob.setKeywords(scheduleJobVo.getKeywords());
            modelMap.put("scheduleJobVo", scheduleJob);
        }

        return "input-schedule-job";
    }

    /**
     * 删除任务
     *
     * @return
     */
    @RequestMapping(value = "delete-schedule-job", method = RequestMethod.GET)
    public String deleteScheduleJob(Long scheduleJobId) {

        scheduleJobService.delete(scheduleJobId);

        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 运行一次
     *
     * @return
     */
    @RequestMapping(value = "run-once-schedule-job", method = RequestMethod.GET)
    public String runOnceScheduleJob(Long scheduleJobId) {

        scheduleJobService.runOnce(scheduleJobId);

        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 暂停
     *
     * @return
     */
    @RequestMapping(value = "pause-schedule-job", method = RequestMethod.GET)
    public String pauseScheduleJob(Long scheduleJobId) {
        scheduleJobService.pauseJob(scheduleJobId);
        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 恢复
     *
     * @return
     */
    @RequestMapping(value = "resume-schedule-job", method = RequestMethod.GET)
    public String resumeScheduleJob(Long scheduleJobId) {
        scheduleJobService.resumeJob(scheduleJobId);
        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 保存任务
     *
     * @param scheduleJobVo
     * @return
     */
    @RequestMapping(value = "save-schedule-job", method = RequestMethod.POST)
    public String saveScheduleJob(ScheduleJobVo scheduleJobVo) {

        //测试用随便设个状态
        scheduleJobVo.setStatus("1");

        if (scheduleJobVo.getScheduleJobId() == null) {
            scheduleJobService.insert(scheduleJobVo);
        } else if (StringUtils.equalsIgnoreCase(scheduleJobVo.getKeywords(),"delUpdate")){
            //直接拿keywords存一下，就不另外重新弄了
            scheduleJobService.delUpdate(scheduleJobVo);
        }else {
            scheduleJobService.update(scheduleJobVo);
        }
        return "redirect:list-schedule-job.shtml";
    }

    /**
     * 任务列表页
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "list-schedule-job", method = RequestMethod.GET)
    public String listScheduleJob(ScheduleJobVo scheduleJobVo, ModelMap modelMap) {

        List<ScheduleJobVo> scheduleJobVoList = scheduleJobService.queryList(scheduleJobVo);
        modelMap.put("scheduleJobVoList", scheduleJobVoList);

        List<ScheduleJobVo> executingJobList = scheduleJobService.queryExecutingJobList();
        modelMap.put("executingJobList", executingJobList);

        return "list-schedule-job";
    }

}
