package BookMarket;

public class Admin extends Person {
    private String id = "admin";
    private String password = "Admin1234";

    public Admin(String name, int phone) {
        super(name, phone);
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void menuAdmin() {
        // 관리자 메뉴 동작 코드 작성
        System.out.println("관리자 메뉴 실행 중...");
    }
}
