package com.KoreaIT.java.AM;

import java.beans.beancontext.BeanContext;
import java.text.NumberFormat.Style;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;//스캐너 프로그램의 필요한 프로그램
import java.util.concurrent.CountDownLatch;

import org.w3c.dom.css.CSSRule;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 == ");

		Scanner sc = new Scanner(System.in);

		int lastArticleId = 0; // 들어는 순서되로 번호를 매길 수 있게 만든 변수 ☆무한루프문 안에 있으면 계속 0으로 초기화 된다.

		List<Article> articles = new ArrayList<>();
		// 모든 게시글을 저장하여 가지고 있는 배열 "ArrayList"
		// ☆게시글(값)이 증가하는 만큼 배열에 크기가 값만큼 자동으로 증가하는 배열

		// 무한루프 반복문
		while (true) {
			System.out.printf("명령어 )");
			String cmd = sc.nextLine().trim();
			// ※trim() : 입력받은 명령어의 양쪽의 공백(띄어쓰기)를 없애준다

			if (cmd.length() == 0) {

				System.out.println("명령어를 입력하세요");
				// 들어온 명령어가 '0'이면 입력을 안한거이므로 다시 입력을 받아야함
				continue; // continue: 아래에 있는 명령어를 실행하지않고 위로 올라감
			}

			if (cmd.equals("system exit")) {
				// 입력받은 명령어가 "system exit"일 경우 해당 반복문이 종류
				break; // break: 해당식이 맞을경우 작업을 중단
			}

			if (cmd.equals("article write")) {
				int id = lastArticleId + 1; // id를 통해 입력받은 게시글 수 만큼 1씩 카운트를 목적으로 하는 변수를 만든다.
				lastArticleId = id; // 1씩 카운팅을 위해 대입 ☆대입을 하지않으면 "lastArticleId"값은 계속 0이다

				String regDate = Util.getNowDateStr();

				System.out.println("제목 : ");
				String title = sc.nextLine();
				// 입력받은 명령어를 변수 "title"에 놓는다 ※제목을 저장할려고 만든 변수 : title
				System.out.println("내용 : ");
				String body = sc.nextLine();
				// 입력받은 명령어를 변수 "title"에 놓는다 ※내용을 저장할려고 만든 변수 : body

				System.out.printf("%d번글이 생성되었습니다.\n", id);
				// 위에 해당조건을 모두 만족했으면 글이 생성된거이므로 "글이 생성되었습니다."출력문을 통해 글생성을 알려준다

				Article article = new Article(id, regDate, title, body);
				// 입력받은 번호(id)와 제목(title),내용(body)을 저장하기 위해 class Article를 만들고 해당 정보를 넘겨준다

				articles.add(article); // 입력받은 정보를 배열 " articles " 넣겠다

			} else if (cmd.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
					// 만약 "articles"에 0이 (없는)경우에는 게시글이 없는 경우이므로 "게시글이 없습니다."를 출력한다.

					continue; // 없는 경우에는 게시글을 작성해야 하므로 위에 식으로 올라간다
				}
				System.out.println("번호     ㅣ     제목");
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("%d     ㅣ     %s\n", article.id, article.title);
				}

			} else if (cmd.startsWith("article detail ")) {
				String[] cmdBits = cmd.split(" ");
				// cmdBits[0] == article cmdBits[1] == detail cmdBits[2] == "정수형 숫자"
				int id = Integer.parseInt(cmdBits[2]);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);

					if (article.id == id) {
						foundArticle = article;
						break;// 가장 가까운 반복문 종료
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				} else {
					System.out.printf("%d번 게시물은 존재합니다.\n", id);
					System.out.printf("번호 : %d\n", foundArticle.id);
					System.out.printf("날짜 : %s\n", foundArticle.regDate);
					System.out.printf("제목 : %s\n", foundArticle.title);
					System.out.printf("내용 : %s\n", foundArticle.body);
				}
			} else if (cmd.startsWith("article delete ")) {
				String[] cmdBits = cmd.split(" ");

				int id = Integer.parseInt(cmdBits[2]);

				int foundIndex = -1; // 없는 경우를 대비

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);

					if (article.id == id) {
						foundIndex = i; // 만약 찾고자 하는 게시물이 있다면 변수foundIndex에 찾은 방번호를 넣는다.
						break;// 가장 가까운 반복문 종료
					}
				}

				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}

				// size() => 3
				// index : 0 1 2
				// id : 1 2 3
				articles.remove(foundIndex);
				System.out.printf("%d번 게시물이 삭제 되었습니다\n", id);

			}

			else {
				System.out.println("없는 명령어입니다.");
				// 해당조건문이 다맞지않는 경우에는 없는 명령어를 입력한거므로 반복문을 빠져나가지않고
				// "없는 명령어입니다"를 출력하고 무한루프를 타고 처음부터 올라간다.
			}

		}

		sc.close(); // 스캐너 종료

		System.out.println("== 프로그램 종료 == ");
	}
}

class Article {
	int id;// 게시글 번호
	String title;// 게시글 시간
	String body;// 게시글 제목
	String regDate;// 게시글 내용

	public Article(int id, String regDate, String title, String body) {
		// 넘겨받는 정보를 class Article를 보고 만든 객체(article)에 this.id(등)을통해 정보를 대입하여 저장한다
		// ☆저장의 목적은 호출을 위해 만든다

		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;

	}

}