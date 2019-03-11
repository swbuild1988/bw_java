package com.bandweaver.tunnel.service.common.export;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


public class ExportTask2 implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": ExportTask2");
    }
}
