package cz.tul.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Marek on 16.08.2016.
 */
@Service
@EnableAsync
public class Executor12 {
    public MyAsyncBean getMyAsyncBean() {
        return myAsyncBean;
    }

    public void setMyAsyncBean(MyAsyncBean myAsyncBean) {
        this.myAsyncBean = myAsyncBean;
    }

    @Autowired
    MyAsyncBean myAsyncBean;


    @Value("${my.property}")
    private String test;

    @Scheduled(fixedDelay = 100000)
    public void print() {
        System.out.println("pred " + new Date() + test);
/*
        atributeDao.save();
        Task task= new Task();

        try {

            myAsyncBean.asyn(1);

            myAsyncBean.asyn(2);
            myAsyncBean.asyn(3);

            myAsyncBean.asyn(4);
            myAsyncBean.asyn(5);

            myAsyncBean.asyn(6);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        System.out.println("po " + new Date());
    }
}
