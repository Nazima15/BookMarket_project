package com.market.cart;

import com.market.bookitem.Book;

public class Cart implements CartInterface {

    static final int NUM_BOOK = 3;
    
    public CartItem[] mCartItem = new CartItem[NUM_BOOK];
    public static int mCartCount = 0;

    public Cart() {
        // 생성자 내용이 비어 있음
    }

    // 도서 목록 출력
    public void printBookList(Book[] booklist) {
        for (int i = 0; i < booklist.length; i++) {
            System.out.print(booklist[i].getBookId() + " | ");
            System.out.print(booklist[i].getName() + " | ");
            System.out.print(booklist[i].getUnitPrice() + " | ");
            System.out.print(booklist[i].getAuthor() + " | ");
            System.out.print(booklist[i].getDescription() + " | ");
            System.out.print(booklist[i].getCategory() + " | ");
            System.out.println(booklist[i].getReleaseDate());
        }
    }

    // 도서 장바구니에 추가
    public void insertBook(Book book) {
        mCartItem[mCartCount++] = new CartItem(book);
    }

    // 장바구니 비우기
    public void deleteBook() {
        mCartItem = new CartItem[NUM_BOOK];
        mCartCount = 0;
    }

    // 장바구니 목록 출력
    public void printCart() {
        System.out.println("장바구니 상품 목록:");
        System.out.println("----------------------------");
        System.out.println("도서ID\t수량\t합계");
        System.out.println("----------------------------");
        for (int i = 0; i < mCartCount; i++) {
            System.out.print(" " + mCartItem[i].getBookID() + "\t");
            System.out.print(" " + mCartItem[i].getQuantity() + "\t");
            System.out.println(" " + mCartItem[i].getTotalPrice());
        }
        System.out.println("----------------------------");
    }

    // 이미 장바구니에 있는 도서인지 확인
    public boolean isCartInBook(String bookId) {
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

    // 장바구니에서 항목 제거
    public void removeCart(int numId) {
        CartItem[] cartItem = new CartItem[NUM_BOOK];
        int num = 0;
        for (int i = 0; i < mCartCount; i++) {
            if (numId != i) {
                cartItem[num++] = mCartItem[i];
            }
        }
        mCartCount = num;
        mCartItem = cartItem;
    }
}
