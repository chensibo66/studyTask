package ATM;

import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> list = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("欢迎来到ATM系统！");
            System.out.println("1. 注册账户");
            System.out.println("2. 登录");
            System.out.println("3. 退出");
            System.out.print("请选择操作：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: registerAccount(); break;
                case 2: login(); break;
                case 3: System.out.println("退出系统！"); return;
                default: System.out.println("无效的操作，请重新选择！"); break;
            }
        }
    }

    private void registerAccount() {
        scanner.nextLine(); // Consume newline
        System.out.print("请输入用户名：");
        String name = scanner.nextLine();
        System.out.print("请输入性别：");
        String gender = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();
        System.out.print("请确认密码：");
        String confirmPassword = scanner.nextLine();
        while (!password.equals(confirmPassword)) {
            System.out.println("密码不一致，请重新输入！");
            System.out.print("请输入密码：");
            password = scanner.nextLine();
            System.out.print("请确认密码：");
            confirmPassword = scanner.nextLine();
        }
        System.out.print("请输入每次取款额度：");
        double withdrawLimit = scanner.nextDouble();

        Account account = new Account(name, gender, password, withdrawLimit);
        list.add(account);
        System.out.println("账户注册成功，ID: " + account.getId());
    }

    private void login() {
        if (list.isEmpty()) {
            System.out.println("没有注册的用户！");
            return;
        }

        scanner.nextLine(); // Consume newline
        System.out.print("请输入账户ID：");
        String id = scanner.nextLine();
        Account account = findAccountById(id);
        if (account == null) {
            System.out.println("账户不存在，请重新输入！");
            return;
        }

        System.out.print("请输入密码：");
        String password = scanner.nextLine();
        while (!account.getPassword().equals(password)) {
            System.out.println("密码错误，请重新输入！");
            password = scanner.nextLine();
        }

        System.out.println("登录成功，" + (account.getGender().equals("男") ? "先生" : "女士") + "，欢迎进入系统！");
        accountOperations(account);
    }

    private Account findAccountById(String id) {
        for (Account account : list) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    private void accountOperations(Account account) {
        while (true) {
            System.out.println("\n请选择操作：");
            System.out.println("1. 展示用户信息");
            System.out.println("2. 存钱");
            System.out.println("3. 取钱");
            System.out.println("4. 转账");
            System.out.println("5. 修改密码");
            System.out.println("6. 退出");
            System.out.print("请输入选择：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: System.out.println(account); break;
                case 2: depositMoney(account); break;
                case 3: withdrawMoney(account); break;
                case 4: transferMoney(account); break;
                case 5: changePassword(account); break;
                case 6: System.out.println("退出登录！"); return;
                default: System.out.println("无效的选择，请重新输入！"); break;
            }
        }
    }

    private void depositMoney(Account account) {
        System.out.print("请输入存款金额：");
        double amount = scanner.nextDouble();
        account.setBalance(account.getBalance() + amount);
        System.out.println("存款成功，当前余额为：" + account.getBalance());
    }

    private void withdrawMoney(Account account) {
        System.out.print("请输入取款金额：");
        double amount = scanner.nextDouble();
        if (amount > account.getBalance()) {
            System.out.println("余额不足！");
            return;
        }
        if (amount > account.getWithdrawLimit()) {
            System.out.println("超过取款限额！");
            return;
        }
        account.setBalance(account.getBalance() - amount);
        System.out.println("取款成功，当前余额为：" + account.getBalance());
    }

    private void transferMoney(Account account) {
        if (list.size() < 2) {
            System.out.println("系统中至少需要两个用户才能进行转账！");
            return;
        }

        System.out.print("请输入对方账户ID：");
        scanner.nextLine(); // Consume newline
        String targetId = scanner.nextLine();

        if (account.getId().equals(targetId)) {
            System.out.println("不能转账给自己！");
            return;
        }

        Account targetAccount = findAccountById(targetId);
        if (targetAccount == null) {
            System.out.println("账户不存在！");
            return;
        }

        System.out.print("请输入对方姓氏：");
        String targetName = scanner.nextLine();
        if (!targetName.equals(targetAccount.getName())) {
            System.out.println("姓氏不匹配！");
            return;
        }

        System.out.print("请输入转账金额：");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("转账金额无效！");
            return;
        }
        if (amount > account.getWithdrawLimit()) {
            System.out.println("超过转账限额！");
            return;
        }
        if (amount > account.getBalance()) {
            System.out.println("余额不足！");
            return;
        }

        account.setBalance(account.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);
        System.out.println("转账成功！");
    }

    private void changePassword(Account account) {
        scanner.nextLine(); // Consume newline
        System.out.print("请输入旧密码：");
        String oldPassword = scanner.nextLine();
        while (!account.getPassword().equals(oldPassword)) {
            System.out.println("旧密码错误，请重新输入！");
            oldPassword = scanner.nextLine();
        }

        System.out.print("请输入新密码：");
        String newPassword = scanner.nextLine();
        System.out.print("请确认新密码：");
        String confirmPassword = scanner.nextLine();
        while (!newPassword.equals(confirmPassword)) {
            System.out.println("两次密码不一致，请重新输入！");
            System.out.print("请输入新密码：");
            newPassword = scanner.nextLine();
            System.out.print("请确认新密码：");
            confirmPassword = scanner.nextLine();
        }

        account.setPassword(newPassword);
        System.out.println("密码修改成功！");
    }
}

