package ATM;

import java.util.Random;

    public class Account {
        private String id;          // 账户ID
        private String name;        // 用户名
        private String gender;      // 性别
        private String password;    // 密码
        private double balance;     // 余额
        private double withdrawLimit; // 每次取现额度

        public Account(String name, String gender, String password, double withdrawLimit) {
            this.id = generateId();
            this.name = name;
            this.gender = gender;
            this.password = password;
            this.balance = 0.0;
            this.withdrawLimit = withdrawLimit;
        }

        private String generateId() {
            Random random = new Random();
            StringBuilder id = new StringBuilder();
            id.append(random.nextInt(9) + 1); // First digit (1-9)
            for (int i = 0; i < 7; i++) {
                id.append(random.nextInt(10)); // Next digits (0-9)
            }
            return id.toString();
        }

        // Getters and Setters
        public String getId() { return id; }
        public String getName() { return name; }
        public String getGender() { return gender; }
        public String getPassword() { return password; }
        public double getBalance() { return balance; }
        public void setBalance(double balance) { this.balance = balance; }
        public double getWithdrawLimit() { return withdrawLimit; }
        public void setPassword(String password) { this.password = password; }

        @Override
        public String toString() {
            return "Account ID: " + id + ", Name: " + name + ", Gender: " + gender + ", Balance: " + balance;
        }
    }

