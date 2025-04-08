<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>티켓 결제</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        .container { width: 400px; margin: 50px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        input { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; }
        button { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
    </style>
    <script>
        function validatePayment() {
            let cardNumber = document.getElementById("cardNumber").value;
            let expiryDate = document.getElementById("expiryDate").value;
            let cvc = document.getElementById("cvc").value;
            let address = document.getElementById("address").value;

            if (cardNumber.length !== 16 || isNaN(cardNumber)) {
                alert("유효한 카드 번호를 입력하세요 (16자리 숫자)");
                return false;
            }
            if (!expiryDate.match(/^(0[1-9]|1[0-2])\/\d{2}$/)) {
                alert("유효한 만료일을 입력하세요 (MM/YY)");
                return false;
            }
            if (cvc.length !== 3 || isNaN(cvc)) {
                alert("유효한 CVC 번호를 입력하세요 (3자리 숫자)");
                return false;
            }
            if (address.trim() === "") {
                alert("청구 주소를 입력하세요.");
                return false;
            }
            document.getElementById("paymentForm").submit();
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>티켓 결제</h2>
        <form id="paymentForm" action="ticketingPaymentProc" method="post">
            <label>카드번호:</label>
            <input type="text" id="cardNumber" name="cardNumber" placeholder="1234123412341234" maxlength="16" required>

            <label>만료일 (MM/YY):</label>
            <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY" required>

            <label>CVC:</label>
            <input type="text" id="cvc" name="cvc" placeholder="123" maxlength="3" required>

            <label>청구 주소:</label>
            <input type="text" id="address" name="address" placeholder="주소 입력" required>

            <button type="button" onclick="validatePayment()">결제하기</button>
        </form>
    </div>
</body>
</html>
