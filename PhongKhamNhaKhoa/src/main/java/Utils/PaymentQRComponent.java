package Utils;

import com.sun.net.httpserver.*;
import view.durgStore.DrugBillConfPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class PaymentQRComponent extends JPanel {

    private Runnable onPaymentSuccess; // callback sau khi thanh toán

    public PaymentQRComponent(int amount, String content, Runnable onPaymentSuccess) throws Exception {
        this.onPaymentSuccess = onPaymentSuccess;
        setPreferredSize(new Dimension(700, 300));
        setLayout(new GridBagLayout());

        // Tạo ảnh QR
        BufferedImage qrImage = generateQR(amount, content);
        Image scaled = qrImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaled));
        add(label);

        // Khởi động webhook
        startWebhookServer();
    }

    private BufferedImage generateQR(int amount, String content) throws Exception {
        String bankSlug = "mbbank";
        String account = "0971811857";
        String accountName = "NGO MINH KHOI";

        String encodedContent = URLEncoder.encode(content, StandardCharsets.UTF_8);
        String encodedName = URLEncoder.encode(accountName, StandardCharsets.UTF_8);

        String qrUrl = String.format(
                "https://img.vietqr.io/image/%s-%s-compact.png?amount=%d&addInfo=%s&accountName=%s",
                bankSlug, account, amount, encodedContent, encodedName
        );

        return ImageIO.read(new URL(qrUrl));
    }

    private void startWebhookServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/webhook", new WebhookHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Webhook đang chạy");
    }

    // Handler nội bộ
    private class WebhookHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println("Nhận webhook:");
            System.out.println(body);

            SwingUtilities.invokeLater(() -> {
                removeAll();
                setLayout(new GridBagLayout());
                DrugBillConfPanel success=new DrugBillConfPanel();
                add(success);
                revalidate();
                repaint();

                if (onPaymentSuccess != null) onPaymentSuccess.run();
            });

            // Trả phản hồi
            String response = "{\"message\": \"Webhook OK\"}";
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("QR Thanh toán thử nghiệm");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Giả lập số tiền và nội dung
                int amount = 2000;
                String content = "Thanh toan hoa don #123";

                PaymentQRComponent qrPanel = new PaymentQRComponent(amount, content, () -> {
                    JOptionPane.showMessageDialog(frame, "Cảm ơn bạn đã thanh toán!");
                });

                frame.getContentPane().add(qrPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
