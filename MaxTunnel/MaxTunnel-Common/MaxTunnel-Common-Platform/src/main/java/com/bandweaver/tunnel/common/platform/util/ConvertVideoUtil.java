//package com.bandweaver.tunnel.common.platform.util;
//
//import java.io.IOException;
//
//import com.bandweaver.tunnel.common.platform.log.LogUtil;
//import org.bytedeco.javacpp.avcodec;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameGrabber;
//import sun.rmi.runtime.Log;
//
///**
// * rtsp转rtmp（转封装方式）
// */
//public class ConvertVideoUtil extends Thread {
//    private int listenerNum;
//    private String rtspPath;
//    private String rtmpPath;
//
//    FFmpegFrameGrabber grabber = null;
//    FFmpegFrameRecorder recorder = null;
//    private boolean isGrab = false;
//    int width = -1, height = -1;
//
//    public boolean isGrab() {
//        return isGrab;
//    }
//
//    public void setGrab(boolean grab) {
//        isGrab = grab;
//    }
//
//    public ConvertVideoUtil(String rtspPath, String rtmpPath) {
//        this.listenerNum = 0;
//        this.rtspPath = rtspPath;
//        this.rtmpPath = rtmpPath;
//    }
//
//    private void prepare() {
//        try {
//            /***********************************************************/
//
//            LogUtil.info("抓取器：" + rtspPath);
//            grabber = FFmpegFrameGrabber.createDefault(rtspPath);
//            grabber.setOption("rtsp_transport", "tcp");
//            grabber.setImageWidth(width);
//            grabber.setImageHeight(height);
//            grabber.start();// 开始之后ffmpeg会采集视频信息，之后就可以获取音视频信息
//            LogUtil.info("开始抓取视频信息");
//            if (width < 0 || height < 0) {
//                width = grabber.getImageWidth();
//                height = grabber.getImageHeight();
//            }
//
//            LogUtil.info("imageWidth:" + width);
//            LogUtil.info("imageHeight:" + height);
//
//            /***************************************************************/
//
//            LogUtil.info("记录器：" + rtmpPath);
//            recorder = new FFmpegFrameRecorder(rtmpPath, width, height, 0);
//            recorder.setInterleaved(true);
//            //recorder.setVideoOption("crf","28");
//            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
//            recorder.setFormat("flv"); // rtmp的类型
//            recorder.setFrameRate(25);
//            recorder.setImageWidth(width);
//            recorder.setImageHeight(height);
//            recorder.setPixelFormat(0); // yuv420p
//            LogUtil.info("recorder start");
//            recorder.start();
//
//            /***************************************************************/
//        } catch (Exception e) {
//            LogUtil.info("prepare error:" + e.toString());
//        }
//    }
//
//    private void over() {
//        try {
//            LogUtil.info("Over");
//            grabber.stop();
//            grabber.release();
//            recorder.stop();
//            recorder.release();
//        } catch (Exception e) {
//            LogUtil.info("over error:" + e.toString());
//        }
//    }
//
//    @Override
//    public void run() {
//        try {
//            prepare();
//
//            while (true && (!this.isInterrupted())) {
//                if (isGrab) {
//                    Frame frame = grabber.grabImage();
//                    if (frame == null) {
//                        //空包记录次数跳过
//                        continue;
//                    }
//                    recorder.record(frame);
//                } else {
//                    LogUtil.info("不抓取，休息下");
//                    Thread.sleep(2000);
//                }
//            }
//
//            /***************************************************************/
//            LogUtil.info("转换结束");
//
//            over();
//
//        } catch (Exception e) {
//            LogUtil.info("异常：" + e.toString());
//            this.interrupt();
//        }
//    }
//
//    /**
//     * 有新的监听者
//     */
//    public void addListener() {
//        listenerNum++;
//    }
//
//    /**
//     * 有监听的关闭
//     */
//    public void delListener() {
//        listenerNum--;
//    }
//
//    public int getListenerNum() {
//        return listenerNum;
//    }
//
//}
