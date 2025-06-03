package com.market.main;

import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.market.bookitem.*;
import com.market.cart.CartItem;
import com.market.member.Admin;
import com.market.member.User;
import com.market.exception.CartException;

public class Welcome {
    static final int NUM_BOOK = 3;
    static final int NUM_ITEM = 7;
    static CartItem[] mCartItem = new CartItem[NUM_BOOK];
    static int mCartCount = 0;
    static User mUser;

    public static void main(String[] args) {
        String[][] mBook = new String[NUM_BOOK][NUM_ITEM];
        Scanner input = new Scanner(System.in);

        System.out.print("당신의 이름을 입력하세요: ");
        String userName = input.nextLine();

        System.out.print("연락처를 입력하세요: ");
        int userMobile = input.nextInt();
        input.nextLine(); // 버퍼 클리어

        mUser = new User(userName, userMobile);

        String greeting = "Welcome to Shopping Mall";
        String tagline = "Welcome to Book Market!";

        boolean quit = false;
        while (!quit) {
            menuIntroduction(greeting, tagline);
            System.out.print("메뉴 번호를 선택하시오: ");
            String menuInput = input.nextLine();

            try {
                int n = Integer.parseInt(menuInput);

                if (n < 1 || n > 9) {
                    throw new CartException("올바르지 않은 메뉴 선택입니다.");
                }

                switch (n) {
                    case 1: menuGuestInfo(); break;
                    case 2: menuCartItemList(); break;
                    case 3: menuCartClear(); break;
                    case 4: menuCartAddItem(mBook); break;
                    case 5: menuCartRemoveItemCount(); break;
                    case 6: menuCartRemoveItem(); break;
                    case 7: menuCartBill(); break;
                    case 8: menuExit(); quit = true; break;
                    case 9: menuAdminLogin(); break;
                }
            } catch (CartException e) {
                System.out.println(e);
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 입력입니다. 숫자를 입력하세요.");
            }
        }
    }

    public static void menuIntroduction(String greeting, String tagline) {
        System.out.println("**************************************************");
        System.out.println("\t" + greeting);
        System.out.println("\t" + tagline);
        System.out.println("**************************************************");
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
        try {
            if (mCartCount == 0) throw new CartException("장바구니가 비어 있습니다.");

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
        } catch (CartException e) {
            System.out.println(e);
        }
    }

    public static void menuCartClear() {
        Scanner input = new Scanner(System.in);
        try {
            if (mCartCount == 0) throw new CartException("장바구니가 이미 비어 있습니다.");

            System.out.print("장바구니에 모든 항목을 삭제하겠습니까? Y / N : ");
            String answer = input.nextLine();

            if (answer.equalsIgnoreCase("Y")) {
                mCartCount = 0;
                System.out.println("장바구니에 모든 항목을 삭제했습니다.");
            } else {
                System.out.println("장바구니 비우기를 취소했습니다.");
            }
        } catch (CartException e) {
            System.out.println(e);
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
        for (int i = 0; i < mCartCount; i++) {
            if (bookId.equals(mCartItem[i].getBookID())) {
                mCartItem[i].setQuantity(mCartItem[i].getQuantity() + 1);
                return true;
            }
        }
        return false;
    }

    public static void menuCartRemoveItemCount() {
        System.out.println("5. 장바구니의 항목 수량 줄이기 - (기능 미구현)");
    }

    public static void menuCartRemoveItem() {
        try {
            if (mCartCount == 0) throw new CartException("장바구니가 비어 있습니다.");
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
        } catch (CartException e) {
            System.out.println(e);
        }
    }

    public static void menuCartBill() {
        try {
            if (mCartCount == 0) throw new CartException("장바구니가 비어 있습니다. 주문할 수 없습니다.");
            Scanner sc = new Scanner(System.in);
            System.out.print("배송받을 분은 고객 정보와 같습니까? (Y/N): ");
            String answer = sc.nextLine().trim();

            String dName, dPhone, dAddress;

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

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String strDate = sdf.format(new Date());

            System.out.println("=============== 배송 받을 고객 정보 ===============");
            System.out.println("고객명: " + dName);
            System.out.println("연락처: " + dPhone);
            System.out.println("배송지: " + dAddress);
            System.out.println("발송일: " + strDate);
            System.out.println("-----------------------------------");

            System.out.println("장바구니 상품 목록: ");
            System.out.printf("%-10s %-20s %-5s %-10s\n", "도서ID", "제목", "수량", "합계");

            int total = 0;
            for (int i = 0; i < mCartCount; i++) {
                CartItem item = mCartItem[i];
                Book book = item.getBook();
                int subtotal = item.getTotalPrice();
                total += subtotal;

                System.out.printf("%-10s %-20s %-5d %-10d\n",
                    book.getBookId(), book.getName(), item.getQuantity(), subtotal);
            }

            System.out.println("-----------------------------------");
            System.out.println("총 결제 금액: " + total + "원");
            mCartCount = 0;
        } catch (CartException e) {
            System.out.println(e);
        }
    }

    public static void menuExit() {
        System.out.println("8. 프로그램을 종료합니다.");
    }

    public static void menuAdminLogin() {
        Scanner input = new Scanner(System.in);
        System.out.println("관리자 정보를 입력하세요:");
        System.out.print("아이디: ");
        String adminId = input.nextLine();
        System.out.print("비밀번호: ");
        String adminPW = input.nextLine();

        Admin admin = new Admin(mUser.getName(), mUser.getPhone());
        if (adminId.equals(admin.getId()) && adminPW.equals(admin.getPassword())) {
            System.out.println("새 도서 정보를 입력하세요:");
            String[] newBook = new String[NUM_ITEM];
            System.out.print("도서 ID: "); newBook[0] = input.nextLine();
            System.out.print("제목: "); newBook[1] = input.nextLine();
            System.out.print("가격: "); newBook[2] = input.nextLine();
            System.out.print("저자: "); newBook[3] = input.nextLine();
            System.out.print("설명: "); newBook[4] = input.nextLine();
            System.out.print("분류: "); newBook[5] = input.nextLine();
            System.out.print("출판일: "); newBook[6] = input.nextLine();
            saveBookToFile(newBook);
            System.out.println("새 도서 정보가 저장되었습니다.");
        } else {
            System.out.println("관리자 정보가 일치하지 않습니다.");
        }
    }

    public static void BookList(String[][] book) {
        setFileToBookList(book);
    }

    public static void saveBookToFile(String[] newBook) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("book.txt", true))) {
            bw.write(String.join(",", newBook));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }

    public static void setFileToBookList(String[][] book) {
        try (BufferedReader br = new BufferedReader(new FileReader("book.txt"))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < NUM_BOOK) {
                String[] tokens = line.split(",");
                for (int col = 0; col < tokens.length; col++) {
                    book[row][col] = tokens[col];
                }
                row++;
            }
        } catch (IOException e) {
            System.out.println("도서 정보를 파일에서 읽는 중 오류 발생: " + e.getMessage());
        }
    }

    public static int totalFileToBookList() {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("book.txt"))) {
            while (br.readLine() != null) {
                total++;
            }
        } catch (IOException e) {
            System.out.println("파일을 읽는 도중 오류 발생: " + e.getMessage());
        }
        return total;
    }
}
