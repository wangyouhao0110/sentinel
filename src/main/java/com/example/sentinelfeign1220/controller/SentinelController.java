package com.example.sentinelfeign1220.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping({"/hey"})
public class SentinelController {

    @ResponseBody
    @GetMapping({"/hhh"})
    public String getSomething() throws InterruptedException {
        String client = this.client();
        return client;
    }

    @ResponseBody
    @GetMapping({"/zzz"})
    @SentinelResource(value = "getClient", blockHandler = "checkGetClient")
    public String client() throws InterruptedException {
        Thread.sleep(3000);
        log.info("调用：client方法");
        return "success";
    }

    @ResponseBody
    @GetMapping({"/xxx"})
    @SentinelResource(value = "getClient2")
    public String client2() throws InterruptedException {
        Thread.sleep(3000);
        log.info("调用：client2方法");
        return "success";
    }

    public String checkGetClient(BlockException e) {
        log.error("被限流了。。。。。。");
        return "error, System is busy ...";
    }
}
