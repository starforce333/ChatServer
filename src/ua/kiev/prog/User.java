package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class User {

    private String login;
    private String from;
    private String password;
    private int cmd;
    private StatusList status = new StatusList();
    private int flag = 0;

    private static Map<String, String> users = new HashMap<String, String>();

    static {
        users.put("admin", "pass");
        users.put("1", "2");
        users.put("user1", "pass1");
        users.put("user2", "pass2");
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public User(String login, String password, int cmd) {
        this.login = login;
        this.password = password;
        this.cmd = cmd;
    }

    public User(String login, int cmd) {
        this.login = login;
        this.cmd = cmd;
    }

    public User(int cmd) {
        this.cmd = cmd;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public static User fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, User.class);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        User user = User.fromJSON(bufStr);
        String tmp = users.get("login");

        if (user.getLogin().equals(tmp))
            resp.setStatus(HttpServletResponse.SC_OK);
        else
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static Map<String, String> getUsers() {
        return users;
    }

}
