<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ptit.pttk.assignment.model.CustomerStat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thống kê khách hàng theo doanh thu</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        .form { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; align-items: end; }
        .form-group { display: flex; flex-direction: column; }
        label { font-weight: bold; margin-bottom: 6px; }
        input[type="date"] { padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        .actions { display: flex; gap: 12px; justify-content: center; margin-top: 16px; }
        .btn { background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; }
        .btn:hover { background-color: #0069d9; }
        .error { color: #dc3545; margin-bottom: 16px; padding: 12px; background-color: #f8d7da; border-radius: 4px; }
        .stats-info { margin-top: 20px; margin-bottom: 16px; color: #6c757d; font-size: 14px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #007bff; color: white; font-weight: bold; }
        tr:hover { background-color: #f5f5f5; }
        .customer-link { color: #007bff; text-decoration: none; cursor: pointer; }
        .customer-link:hover { text-decoration: underline; }
        .text-right { text-align: right; }
        .total-row { background-color: #f0f8ff; font-weight: bold; }
        .back-btn { background-color: #6c757d; }
        .back-btn:hover { background-color: #5a6268; }
    </style>
</head>
<body>
<div>
    <div style="margin-bottom: 16px;">
        <a href="${pageContext.request.contextPath}/select-stat" class="btn back-btn">← Quay lại</a>
    </div>
    <h2>Thống kê khách hàng theo doanh thu</h2>
    <form class="form" method="post" action="${pageContext.request.contextPath}/customer-stat">
        <div class="form-group">
            <label for="startDate">Ngày bắt đầu</label>
            <input type="date" id="startDate" name="startDate" value="${startDate != null ? startDate : ''}" required>
        </div>
        <div class="form-group">
            <label for="endDate">Ngày kết thúc</label>
            <input type="date" id="endDate" name="endDate" value="${endDate != null ? endDate : ''}" required>
        </div>
        <div class="actions" style="grid-column: 1 / -1;">
            <button type="submit" class="btn">Xem thống kê</button>
        </div>
    </form>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <% 
    List<CustomerStat> customerStats = (List<CustomerStat>) request.getAttribute("customerStats");
    if (customerStats != null && !customerStats.isEmpty()) { 
        String startDate = (String) request.getAttribute("startDate");
        String endDate = (String) request.getAttribute("endDate");
        
        // Tính tổng doanh thu
        double totalRevenue = 0.0;
        for (CustomerStat stat : customerStats) {
            if (stat.getTotalSpent() != null) {
                totalRevenue += stat.getTotalSpent();
            }
        }
    %>
    <div class="stats-info">
        <strong>Kết quả thống kê từ <%= startDate %> đến <%= endDate %>:</strong>
    </div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Họ và tên</th>
                <th>Mã khách hàng</th>
                <th class="text-right">Số lần mua hàng</th>
                <th class="text-right">Tổng tiền thanh toán</th>
            </tr>
        </thead>
        <tbody>
            <% for (CustomerStat stat : customerStats) { %>
            <tr>
                <td><%= stat.getCustomerId() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/transactions-of-customer?customerId=<%= stat.getCustomerId() %>&customerCode=<%= stat.getCustomerCode() %>&customerName=<%= java.net.URLEncoder.encode(stat.getCustomerName() != null ? stat.getCustomerName() : "", "UTF-8") %>&startDate=<%= startDate %>&endDate=<%= endDate %>" class="customer-link">
                        <%= stat.getCustomerName() != null ? stat.getCustomerName() : "" %>
                    </a>
                </td>
                <td><%= stat.getCustomerCode() != null ? stat.getCustomerCode() : "" %></td>
                <td class="text-right"><%= stat.getPurchasedCount() != null ? stat.getPurchasedCount() : 0 %></td>
                <td class="text-right">
                    <%= stat.getTotalSpent() != null ? String.format("%,.0f", stat.getTotalSpent()) : "0" %> VNĐ
                </td>
            </tr>
            <% } %>
            <tr class="total-row">
                <td class="text-right" colspan="5"><strong>Tổng doanh thu: <%= String.format("%,.0f", totalRevenue) %> VNĐ</strong></td>
<%--                <td class="text-right"><strong><%= String.format("%,.0f", totalRevenue) %> VNĐ</strong></td>--%>
            </tr>
        </tbody>
    </table>
    <% } else if (customerStats != null && customerStats.isEmpty()) { %>
    <div class="stats-info" style="text-align: center; margin-top: 20px;">
        Không có dữ liệu thống kê trong khoảng thời gian đã chọn.
    </div>
    <% } %>
</div>
</body>
</html>


