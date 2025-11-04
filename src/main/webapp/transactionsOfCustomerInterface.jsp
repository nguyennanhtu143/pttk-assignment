<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ptit.pttk.assignment.model.Invoice" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách hóa đơn khách hàng</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        .error { color: #dc3545; margin-bottom: 16px; padding: 12px; background-color: #f8d7da; border-radius: 4px; }
        .back-btn { background-color: #6c757d; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; display: inline-block; margin-bottom: 16px; }
        .back-btn:hover { background-color: #5a6268; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #007bff; color: white; font-weight: bold; }
        tr:hover { background-color: #f5f5f5; }
        .invoice-link { color: #007bff; text-decoration: none; cursor: pointer; }
        .invoice-link:hover { text-decoration: underline; }
        .text-right { text-align: right; }
        .no-data { text-align: center; color: #6c757d; margin-top: 20px; padding: 20px; }
    </style>
</head>
<body>
<div>
    <div>
        <a href="javascript:history.back()" class="back-btn">← Quay lại</a>
    </div>
    <h2>Danh sách hóa đơn khách hàng</h2>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <% 
    List<Invoice> invoices = (List<Invoice>) request.getAttribute("invoices");
//    Integer customerId = (Integer) request.getAttribute("customerId");
    String customerName = (String) request.getAttribute("customerName");
    String customerCode = (String) request.getAttribute("customerCode");
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    if (invoices != null && !invoices.isEmpty()) { 
    %>
    <div style="margin-bottom: 16px; color: #6c757d;">
        <strong>Khách hàng: <%= customerName != null ? customerName : "" %> </strong>
        <br/>
        <strong>Mã khách hàng: <%= customerCode != null ? customerCode : "" %></strong>
        <% if (startDate != null && endDate != null) { %>
        <br><span>Khoảng thời gian: <%= startDate %> đến <%= endDate %></span>
        <% } %>
    </div>
    <table>
        <thead>
            <tr>
                <th>Mã hóa đơn</th>
                <th>Ngày lập</th>
                <th class="text-right">Tổng tiền</th>
                <th>Chi tiết</th>
            </tr>
        </thead>
        <tbody>
            <% for (Invoice invoice : invoices) { %>
            <tr>
                <td><%= invoice.getId() %></td>
                <td><%= invoice.getInvoiceDate() != null ? dateFormat.format(invoice.getInvoiceDate()) : "" %></td>
                <td class="text-right">
                    <%= invoice.getTotalAmount() != null ? String.format("%,.0f", invoice.getTotalAmount()) : "0" %> VNĐ
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/invoice-detail?invoiceId=<%= invoice.getId() %>&customerName=<%= java.net.URLEncoder.encode(customerName != null ? customerName : "", "UTF-8") %>&customerCode=<%= customerCode %>" class="invoice-link">
                        Xem chi tiết
                    </a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
    <% } else if (invoices != null && invoices.isEmpty()) { %>
    <div style="margin-bottom: 16px; color: #6c757d;">
        <strong>Mã khách hàng: <%= customerCode %></strong>
        <% if (startDate != null && endDate != null) { %>
        <br><span>Khoảng thời gian: <%= startDate %> đến <%= endDate %></span>
        <% } %>
    </div>
    <div class="no-data">
        Khách hàng này không có hóa đơn nào trong khoảng thời gian đã chọn.
    </div>
    <% } %>
</div>
</body>
</html>

