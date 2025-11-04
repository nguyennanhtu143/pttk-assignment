<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ptit.pttk.assignment.model.InvoiceDetail" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết hóa đơn</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }
        .container { max-width: 1100px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        .error { color: #dc3545; margin-bottom: 16px; padding: 12px; background-color: #f8d7da; border-radius: 4px; }
        .back-btn { background-color: #6c757d; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; display: inline-block; margin-bottom: 16px; }
        .back-btn:hover { background-color: #5a6268; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #007bff; color: white; font-weight: bold; }
        tr:hover { background-color: #f5f5f5; }
        .text-right { text-align: right; }
        .total-row { background-color: #f0f8ff; font-weight: bold; }
        .meta { color: #6c757d; margin-bottom: 8px; }
    </style>
</head>
<body>
<div>
    <div>
        <a href="javascript:history.back()" class="back-btn">← Quay lại</a>
    </div>
    <h2>Chi tiết hóa đơn</h2>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <% 
    List<InvoiceDetail> invoiceDetails = (List<InvoiceDetail>) request.getAttribute("invoiceDetails");
    String invoiceId = request.getParameter("invoiceId");
    if (invoiceDetails != null) { 
        double grandTotal = 0.0;
        for (InvoiceDetail d : invoiceDetails) {
            if (d.getTotalPrice() != null) grandTotal += d.getTotalPrice();
        }
    %>
    <div class="meta">
        Mã hóa đơn: <strong><%= invoiceId != null ? invoiceId : "" %></strong>
        <% String customerName = (String) request.getAttribute("customerName"); %>
        <% String customerCode = (String) request.getAttribute("customerCode"); %>
        <br/>
        Khách hàng: <strong><%= customerName != null ? customerName : "" %></strong>
        <br/>
        Mã khách hàng: <strong><%= customerCode != null ? customerCode : "" %></strong>

    </div>
    <% if (!invoiceDetails.isEmpty()) { %>
    <table>
        <thead>
            <tr>
                <th>STT</th>
                <th>Tên mặt hàng</th>
                <th class="text-right">Số lượng</th>
                <th class="text-right">Đơn giá</th>
                <th class="text-right">Thành tiền</th>
            </tr>
        </thead>
        <tbody>
        <% int idx = 1; for (InvoiceDetail d : invoiceDetails) { %>
            <tr>
                <td><%= idx++ %></td>
                <td><%= d.getProductName() != null ? d.getProductName() : "" %></td>
                <td class="text-right"><%= d.getQuantity() != null ? d.getQuantity() : 0 %></td>
                <td class="text-right"><%= d.getUnitPrice() != null ? String.format("%,.0f", d.getUnitPrice()) : "0" %> VNĐ</td>
                <td class="text-right"><%= d.getTotalPrice() != null ? String.format("%,.0f", d.getTotalPrice()) : "0" %> VNĐ</td>
            </tr>
        <% } %>
            <tr class="total-row">
                <td colspan="4" class="text-right">Tổng cộng</td>
                <td class="text-right"><strong><%= String.format("%,.0f", grandTotal) %> VNĐ</strong></td>
            </tr>
        </tbody>
    </table>
    <% } else { %>
    <div class="meta">Hóa đơn không có dòng chi tiết.</div>
    <% } %>
    <% } %>
</div>
</body>
</html>


