package ua.kiev.prog;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class AuthServlet extends HttpServlet {

   private StatusList statusList = new StatusList();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);
        System.out.println(bufStr);
        User user = User.fromJSON(bufStr);

        String log = user.getLogin();
        int status = user.getCmd();
        String pass = user.getPassword();
        String tmp = User.getUsers().get(log);

        if (status == 0) {
//            user.removeStatus(log);
            statusList.remove(log);
//            return;
        }
        if (status == 2) {
            statusList.setFlag(1);
            resp.setContentType("application/json");

            String online = statusList.toJSON();

            System.out.println("Status 2");
            System.out.println(statusList.getStatusList());

            PrintWriter pw = resp.getWriter();
            pw.print(online);
            System.out.println("online -> " + online);

//            if (online != null) {
//                OutputStream os = resp.getOutputStream();
//                buf = online.getBytes(StandardCharsets.UTF_8);
//                os.write(buf);
//            }
//            return;
        }

        if (pass.equals(tmp)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            statusList.add(log);

            System.out.println("add -> " + statusList.getStatusList());
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

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
}
