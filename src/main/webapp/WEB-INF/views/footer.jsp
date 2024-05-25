
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<head>
    <title>InvestTrackPro</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            color: #333;
        }

        .footer {
            background-color: #2c3e50;
            color: white;
            padding: 20px 0;
            position: relative;
            bottom: 0;
            width: 100%;
        }

        .footer-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
        }

        .footer-column {
            flex: 1;
            min-width: 200px;
            margin: 10px 0;
        }

        .footer-column h3 {
            margin-top: 0;
            color: #f39c12;
        }

        .footer-column ul {
            list-style: none;
            padding: 0;
        }

        .footer-column ul li {
            margin: 5px 0;
        }

        .footer-column ul li a {
            color: white;
            text-decoration: none;
        }

        .footer-column ul li a:hover {
            text-decoration: underline;
        }

        .footer-social {
            display: flex;
            justify-content: center;
            margin-top: 10px;
        }

        .footer-social a {
            margin: 0 10px;
            color: white;
            font-size: 1.5em;
        }

        .footer-social a:hover {
            color: #f39c12;
        }

        .footer-subscribe input[type="email"] {
            padding: 10px;
            border: none;
            border-radius: 4px;
            margin-right: 10px;
            width: 70%;
            max-width: 300px;
        }

        .footer-subscribe button {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            background-color: #f39c12;
            color: white;
            cursor: pointer;
        }

        .footer-subscribe button:hover {
            background-color: #e67e22;
        }

        .footer-bottom {
            text-align: center;
            margin-top: 20px;
            border-top: 1px solid #34495e;
            padding-top: 10px;
        }
    </style>
</head>
<body>
<div class="footer">
    <div class="footer-container">
        <div class="footer-column">
            <h3>InvestTrackPro</h3>
            <p>123 投资大道, 投资城市, 投资国 12345</p>
            <p>电话: (123) 456-7890</p>
            <p>邮箱: support@investtrackpro.com</p>
        </div>
        <div class="footer-column">
            <h3>快速链接</h3>
            <ul>
                <li><a href="#">首页</a></li>
                <li><a href="#">关于我们</a></li>
                <li><a href="#">服务</a></li>
                <li><a href="#">联系我们</a></li>
            </ul>
        </div>
        <div class="footer-column">
            <h3>关注我们</h3>
            <div class="footer-social">
                <a href="#"><i class="fa fa-facebook"></i></a>
                <a href="#"><i class="fa fa-twitter"></i></a>
                <a href="#"><i class="fa fa-linkedin"></i></a>
                <a href="#"><i class="fa fa-instagram"></i></a>
            </div>
        </div>
        <div class="footer-column">
            <h3>订阅</h3>
            <form class="footer-subscribe">
                <input type="email" placeholder="输入您的邮箱地址" required>
                <button type="submit">订阅</button>
            </form>
        </div>
    </div>
    <div class="footer-bottom">
        <p>&copy; 2024 InvestTrackPro. All rights reserved.</p>
    </div>
</div>



