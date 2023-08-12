import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.Executors;

public class UserController {
    private EventBus eventBus;

    public UserController(){
        eventBus = new AsyncEventBus(Executors.newFixedThreadPool(2));
    }

    /**
     * 注意：泛型参数是 Object,而不是接口 Observer
     * @param observerList
     */
    public void setObserverList(List<Object> observerList){
        for(Object observer : observerList){
            eventBus.register(observer);
        }
    }

    public void register(String userName, String passWord){
        // 1、根据用户名密码保存在数据库
        Long userId = saveUser(userName, passWord);
        // 2、如果上一步有结果则通知所有观察者
        if(userId != null){
            eventBus.post(userName);
        }
    }


    public Long saveUser(String userName, String passWord){
        return 1L;
    }
}
