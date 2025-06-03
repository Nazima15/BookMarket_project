package nazi;
import java.util.Scanner;
import com.market.bookitem.*;
import com.market.cart.Cart;
import com.market.member.Admin;
import com.market.member.User;
import com.market.cart.CartItem;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Welcome7 {
    static final int NUM_BOOK = 3;
    static final int NUM_ITEM = 7;
    static CartItem[] mCartItem = new CartItem[NUM_BOOK];
    static int mCartCount = 0;
    static User mUser;

    public static void main(String[] args) {
        String[][] mBook = new String[NUM_BOOK][NUM_ITEM];
        Scanner input = new Scanner(System.in);

        System.out.print("당신의 이름을 입력하세요: ");
        String userName = input.next();

        System.out.print("연락처를 입력하세요: ");
        int userMobile = input.nextInt();

        mUser = new User(userName, userMobile);

        String greeting = "Welcome to Shopping Mall";
        String tagline = "Welcome to Book Market";

        boolean quit = false;
        while (!quit) {
            System.out.println("**************************************************");
            System.out.println("\t" + greeting);
            System.out.println("\t" + tagline);
            System.out.println("**************************************************");

            menuIntroduction();

            System.out.print("메뉴 번호를 선택하시오: ");
            int n = input.nextInt();

            if (n < 1 || n > 9) {
                System.out.println("1부터 9까지의 숫자를 입력하세요");
            } else {
                switch (n) {
                    case 1:
                        menuGuestInfo();
                        break;
                    case 2:
                        menuCartItemList();
                        break;
                    case 3:
                        menuCartClear();
                        break;
                    case 4:
                        menuCartAddItem(mBook);
                        break;
                    case 5:
                        menuCartRemoveItemCount();
                        break;
                    case 6:
                        menuCartRemoveItem();
                        break;
                    case 7:
                        menuCartBill();
                        break;
                    case 8:
                        menuExit();
                        quit = true;
                        break;
                    case 9:
                        menuAdminLogin();
                        break;
                }
            }
        }
    }

    public static void menuIntroduction() {
        System.out.println("1. 고객 정보 확인하기\t2. 장바구니 상품 목록 보기");
        System.out.println("3. 장바구니 비우기\t4. 장바구니에 항목 추가하기");
        System.out.println("5. 장바구니의 항목 수량 줄이기\t6. 장바구니 항목 삭제하기");
        System.out.println("7. 영수증 표시하기\t8. 종료");
        System.out.println("9. 관리자 로그인");
        System.out.println("**************************************************");
    }

    public static void menuGuestInfo() {
        System.out.println("현재 고객 정보:");
        System.out.println("이름: " + mUser.getName() + " 연락처: " + mUser.getPhone());
    }

    public static void menuCartItemList() {
        System.out.println("장바구니 상품 목록:");
        System.out.println("-----------------------------------------");
        System.out.println("도서ID \t| 수량 \t| 합계");
        System.out.println("-----------------------------------------");

        for (int i = 0; i < mCartCount; i++) {
            System.out.printf("%-10s\t| %-5d\t| %-10d\n",
                mCartItem[i].getBookID(),
                mCartItem[i].getQuantity(),
                mCartItem[i].getTotalPrice());
        }

        System.out.println("-----------------------------------------");
    }

    public static void menuCartClear() {
        Scanner input = new Scanner(System.in);
        System.out.print("장바구니에 모든 항목을 삭제하겠습니까? Y / N : ");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            mCartCount = 0;
            System.out.println("장바구니에 모든 항목을 삭제했습니다.");
        } else {
            System.out.println("장바구니 비우기를 취소했습니다.");
        }
    }

    public static void menuCartAddItem(String[][] book) {
        BookList(book);

        System.out.println("도서 목록:");
        for (int i = 0; i < NUM_BOOK; i++) {
            for (int j = 0; j < NUM_ITEM; j++) {
                System.out.print(book[i][j] + " | ");
            }
            System.out.println();
        }

        boolean quit = false;
        Scanner input = new Scanner(System.in);

        while (!quit) {
            System.out.print("장바구니에 추가할 도서의 ID를 입력하세요: ");
            String str = input.nextLine();

            boolean flag = false;
            int numId = -1;

            for (int i = 0; i < NUM_BOOK; i++) {
                if (str.equals(book[i][0])) {
                    numId = i;
                    flag = true;
                    break;
                }
            }

            if (flag) {
                System.out.print("장바구니에 추가하겠습니까? Y / N : ");
                str = input.nextLine();

                if (str.equalsIgnoreCase("Y")) {
                    System.out.println(book[numId][0] + " 도서가 장바구니에 추가되었습니다.");

                    String bookID = book[numId][0];
                    String title = book[numId][1];
                    int price = Integer.parseInt(book[numId][2].trim());
                    String author = book[numId][3];
                    String description = book[numId][4];
                    String category = book[numId][5];
                    String publishDate = book[numId][6];

                    Book newBook = new Book(bookID, title, price, author, description, category, publishDate);

                    if (!isCartInBook(bookID)) {
                        mCartItem[mCartCount++] = new CartItem(newBook);
                    }

                    quit = true;
                } else {
                    System.out.println("다시 입력해 주세요.");
                }
            } else {
                System.out.println("유효한 도서 ID가 아닙니다. 다시 입력해 주세요.");
            }
        }
    }

    public static boolean isCartInBook(String bookId) {
        boolean flag = false;
        for (int i = 0; i < mCartCount; i++) {
            if (bookId.equals(mCartItem[i].getBookID())) {
                mCartItem[i].setQuantity(mCartItem[i].getQuantity() + 1);
                flag = true;
                break;
            }
        }
        return flag;
    }

    // 5번 기능은 미구현 상태 유지
    public static void menuCartRemoveItemCount() {
        System.out.println("5. 장바구니의 항목 수량 줄이기 - (기능 미구현)");
    }

    // 6번: 장바구니에서 특정 항목 삭제 기능 구현
    public static void menuCartRemoveItem() {
        if (mCartCount == 0) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }

        menuCartItemList();

        Scanner input = new Scanner(System.in);
        System.out.print("장바구니에서 삭제할 도서의 ID를 입력하세요: ");
        String removeId = input.nextLine();

        boolean found = false;
        for (int i = 0; i < mCartCount; i++) {
            if (mCartItem[i].getBookID().equals(removeId)) {
                System.out.print(removeId + " 장바구니에서 도서가 삭제되었습니까? Y / N : ");
                String confirm = input.nextLine();

                if (confirm.equalsIgnoreCase("Y")) {
                    // 배열에서 해당 항목 삭제(뒤 항목을 앞으로 당김)
                    for (int j = i; j < mCartCount - 1; j++) {
                        mCartItem[j] = mCartItem[j + 1];
                    }
                    mCartItem[mCartCount - 1] = null;
                    mCartCount--;
                    System.out.println(removeId + " 장바구니에서 도서가 삭제되었습니다.");
                } else {
                    System.out.println("삭제를 취소했습니다.");
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("장바구니에 해당 도서 ID가 없습니다.");
        }
    }

 // 7번 기능 구현 - 영수증 출력
    public static void menuCartBill() {
        if (mCartCount == 0) {
            System.out.println("장바구니가 비어 있습니다. 주문할 수 없습니다.");
            return;
        }

        Scanner sc = new Scanner(System.in);

        // 배송받을 분이 고객 정보와 같은지 묻기
        System.out.print("배송받을 분은 고객 정보와 같습니까? (Y/N): ");
        String answer = sc.nextLine().trim();

        String dName;
        String dPhone;
        String dAddress;

        if (answer.equalsIgnoreCase("Y")) {
            dName = mUser.getName();
            dPhone = String.valueOf(mUser.getPhone());
            dAddress = mUser.getAddress();

            if (dAddress == null || dAddress.trim().isEmpty()) {
                System.out.print("배송지를 입력해주세요: ");
                dAddress = sc.nextLine();
            }
        } else if (answer.equalsIgnoreCase("N")) {
            System.out.print("배송받을 고객명을 입력하세요: ");
            dName = sc.nextLine();

            System.out.print("배송받을 고객의 연락처를 입력하세요: ");
            dPhone = sc.nextLine();

            System.out.print("배송받을 고객의 배송지를 입력해주세요: ");
            dAddress = sc.nextLine();
        } else {
            System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
            return;
        }

        // 날짜 포맷
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = sdf.format(new Date());

        // 배송 정보 출력
        System.out.println("=============== 배송 받을 고객 정보 ===============");
        System.out.println("고객명: " + dName);
        System.out.println("연락처: " + dPhone);
        System.out.println("배송지: " + dAddress);
        System.out.println("발송일: " + strDate);
        System.out.println("-----------------------------------");

        // 장바구니 내역 출력
        System.out.println("장바구니 상품 목록: ");
        System.out.printf("%-10s %-20s %-5s %-10s\n", "도서ID", "제목", "수량", "합계");

        int total = 0;
        for (int i = 0; i < mCartCount; i++) {
            CartItem item = mCartItem[i];
            Book book = item.getBook();  // 필요 시 getItemBook()으로 변경
            int subtotal = item.getTotalPrice();
            total += subtotal;

            System.out.printf("%-10s %-20s %-5d %-10d\n",
                    book.getBookId(),
                    book.getName(),
                    item.getQuantity(),
                    subtotal);
        }
        // 장바구니 초기화
        mCartCount = 0;
    }

    public static void menuExit() {
        System.out.println("8. 프로그램을 종료합니다.");
    }

    public static void menuAdminLogin() {
        System.out.println("관리자 정보를 입력하세요:");

        Scanner input = new Scanner(System.in);
        System.out.print("아이디: ");
        String adminId = input.next();

        System.out.print("비밀번호: ");
        String adminPW = input.next();

        Admin admin = new Admin(mUser.getName(), mUser.getPhone());
        if (adminId.equals(admin.getId()) && adminPW.equals(admin.getPassword())) {
            System.out.println("이름: " + admin.getName() + " 연락처: " + admin.getPhone());
            System.out.println("아이디: " + admin.getId() + " 비밀번호: " + admin.getPassword());
        } else {
            System.out.println("관리자 정보가 일치하지 않습니다.");
        }
    }

    public static void BookList(String[][] book) {
        book[0][0] = "ISBN1234";
        book[0][1] = "쉽게 배우는 JSP 프로그래밍";
        book[0][2] = "27000";
        book[0][3] = "송미영";
        book[0][4] = "단계별로 쉽게 배우는 JSP 프로그래밍";
        book[0][5] = "컴퓨터/IT";
        book[0][6] = "2020-05-10";

        book[1][0] = "ISBN5678";
        book[1][1] = "자바스크립트 입문";
        book[1][2] = "22000";
        book[1][3] = "홍길동";
        book[1][4] = "초보자를 위한 자바스크립트 입문서";
        book[1][5] = "컴퓨터/IT";
        book[1][6] = "2019-03-15";

        book[2][0] = "ISBN9012";
        book[2][1] = "파이썬 데이터 분석";
        book[2][2] = "35000";
        book[2][3] = "이영희";
        book[2][4] = "파이썬을 활용한 데이터 분석 실무";
        book[2][5] = "컴퓨터/IT";
        book[2][6] = "2021-08-20";
    }
}

