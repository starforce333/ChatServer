package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class StatusList {

    private List<String> statusList = new ArrayList<>();
    int flag = 0;

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public synchronized void add(String user) {
        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i).equals(user)) {
                return;
            }
        }
        statusList.add(user);
    }

    public synchronized void remove(String user) {
        statusList.remove(user);
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("[");
        for (String s : statusList) {
            tmp.append(s);
            tmp.append(',');

        }
        tmp.append("]");
        return "StatusList: " + tmp.toString();
    }
}
