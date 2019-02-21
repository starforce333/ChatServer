package ua.kiev.prog;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetListServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();
    private StatusList statusList = new StatusList();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        if (statusList.getFlag() == 1) {
            statusList.setFlag(0);
            System.out.println("flag" + 1);

            resp.setContentType("application/json");

            String json = statusList.toJSON();

            if (json != null) {
                OutputStream os = resp.getOutputStream();
                byte[] buf = json.getBytes(StandardCharsets.UTF_8);
                os.write(buf);
            }
        }
        if (statusList.getFlag() == 0) {

            String fromStr = req.getParameter("from");
            int from = 0;

            try {
                from = Integer.parseInt(fromStr);
                if (from < 0) from = 0;
            } catch (Exception ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            resp.setContentType("application/json");

            String json = msgList.toJSON(from);

            if (json != null) {
                OutputStream os = resp.getOutputStream();
                byte[] buf = json.getBytes(StandardCharsets.UTF_8);
                os.write(buf);

                //PrintWriter pw = resp.getWriter();
                //pw.print(json);
            }
        }
    }
}
