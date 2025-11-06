<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giao diện chính</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }
        .container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        .actions { display: flex; gap: 16px; justify-content: center; }
        .btn { background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; }
        .btn:hover { background-color: #0069d9; }
    </style>
    </head>
<body>
    <div>
        <h2>Điện máy vjp pro</h2>
        <div class="actions">
            <a class="btn" href="${pageContext.request.contextPath}/register" style="margin-top: 40px">Đăng ký thành viên</a>
        </div>
    </div>
</body>
</html>












