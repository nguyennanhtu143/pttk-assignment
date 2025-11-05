<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chọn chức năng thống kê</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }
        .container { max-width: 640px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        .actions { display: flex; flex-direction: column; gap: 12px; align-items: center; }
        .btn { background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; display: inline-block; text-align: center; width: auto; max-width: 320px; white-space: normal; }
        .btn:hover { background-color: #0069d9; }
    </style>
</head>
<body>
<div>
    <h2>Chọn chức năng thống kê</h2>
    <div style="margin-bottom: 16px;">
        <a href="${pageContext.request.contextPath}/mainManagerInterface.jsp" class="btn back-btn">← Quay lại</a>
    </div>
    <div class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/customer-stat" style="margin-top: 40px;">Thống kê khách hàng theo doanh thu</a>
    </div>
</div>
</body>
</html>


