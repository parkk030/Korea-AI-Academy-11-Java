package com.korai.study.ch11;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

public class TodoMain {
    public static int pathIndex = 0;
    public static boolean running = true;
    public static void main(String[] args) {
        List<View> viewList = new ArrayList<>();
        viewList.add(HomeView.getInstance());
        viewList.add(RegisterView.getInstance());
        viewList.add(ListView.getInstance());

        Scanner scanner = new Scanner(System.in);

        while(running){
            viewList.get(pathIndex).show();

        }
    }
}
//object 클래스
@Data
@NoArgsConstructor
@AllArgsConstructor
//정보 관리 도와줄 entity
class Todo {
    private int id;
    private String content;

}


// 기능 추상화
// 추상화 -혼자생성x , 구현객체 어딘가존재(implement)
interface View {
    void show();
}

// 싱글톤 - 하나의 유일한객체를 만들고 계속 재사용 -

//class - 생성(메모리에 인스턴스 실존) 가능
class HomeView implements View {
    //싱글톤 2번 - 해당 클래스 자료형의 인스턴스를 담아두는 static 변수선언한다.
    // 하나만 new, 이름은 instance , private 으로 외부접근제한
    private static HomeView instance; // new - hip -남은메모리 빌림


    // 싱글톤 1번 - 생성자를 private로 정의한다.  , 생성자를 숨겨 외부에서 생성을 제한
    private HomeView(){

    }

    //싱글톤 3번 - static 변수 homeView가 null일 때 인스턴스를 생성 -메모리없을때대비
    // static getter를 통해서만 접근할수있게 유도
    public static HomeView getInstance(){
        while(instance==null){
            instance = new HomeView();
        }
        return instance;
    }

    @Override
    public void show() {
        String menu;
        Scanner scanner = new Scanner(System.in);
        System.out.println("[ 홈 화면 ]");
        System.out.println("R. 등록");
        System.out.println("L. 조회");
        System.out.println("Q. 종료");
        System.out.println(">>>>>>>");
        menu = scanner.nextLine().trim(); //trim 좌우공백제거
        menu = String.valueOf(menu.charAt(0)); //string으로 변환 , match,equals 적용시키기위함
        boolean isAlphabet = menu.matches("[a-zA-Z]"); // charat(0) 첫글자가져오기 ,
        //match 비교 후 true false - 정규표현식 ,
        if(!isAlphabet){
            System.out.println("영문자만 입력 가능 합니다.");
        }
        menu = menu.toUpperCase();
        if("R".equals(menu)){
            TodoMain.pathIndex=1;
        }
        else if("L".equals(menu)){
            TodoMain.pathIndex=2;
        }
        else if("Q".equals(menu)){
            TodoMain.running=false;
        }
        else System.out.println("다시입력하세요.");


    }
}

// 싱글톤 1번 - 생성자를 private로 정의한다.  , 생성자를 숨겨 외부에서 생성을 제한
//싱글톤 2번 - 해당 클래스 자료형의 인스턴스를 담아두는 static 변수선언한다.
// 하나만 new, 이름은 instance , private 으로 외부접근제한
//싱글톤 3번 - static 변수 homeView가 null일 때 인스턴스를 생성 -메모리없을때대비
// static getter를 통해서만 접근할수있게 유도

class RegisterView implements View {
    private static RegisterView instance;

private RegisterView(){

}

public static RegisterView getInstance(){
    if(instance == null){
        instance = new RegisterView();
    }
    return instance;
}

    @Override
    public void show() {
        Scanner scanner = new Scanner(System.in);
        String content;
        System.out.println("[ 등록 화면 ]");
        System.out.println("내용을 입력하세요.");
        System.out.println(">>>");
        content = scanner.nextLine().trim();
        Todo newTodo = new Todo();
        newTodo.setContent(content);
        TodoRepository.getInstance().insert(newTodo);
        System.out.println("TODO 등록 완료.");
        TodoMain.pathIndex=0;


    }
}


class ListView implements View {
    private static ListView instance;

    private ListView(){

    }

    public static ListView getInstance(){
        while(instance == null){
            instance = new ListView();
        }
        return instance;
    }

    @Override
    public void show() {
        System.out.println("[ 목록 화면 ]");
        List<Todo> todos = TodoRepository.getInstance().findAll();
        if(todos.size()==0){
            System.out.println("등록된 TODO 없음");
            TodoMain.pathIndex=0;
            return;
        }
        for(Todo todo : todos){
            System.out.println(todo);
        }
        TodoMain.pathIndex=0;
    }
}

interface  Repository<T>{
    void insert(T t);
    void update(T t);
    T delete(int id);
    T findById(int id);
    List<T> findAll();
}

class TodoRepository implements Repository<Todo> {
    private static TodoRepository instance;
    private int lastId;
    private List<Todo> todos;

    private TodoRepository() {
    }

    public static TodoRepository getInstance(){
        if(instance==null){
            instance = new TodoRepository();
        }
        return instance;
    }

    @Override
    public void insert(Todo todo) {
        todo.setId(lastId++);
        todos.add(todo);
    }

    @Override
    public void update(Todo todo) {
        for(int i =0; i<todos.size();i++){
            if(todos.get(i).getId() == todo.getId()){
                todos.set(i,todo);
                break;
            }
        }
    }

    @Override
    public Todo delete(int id) {
        for(int i =0; i<todos.size();i++){
            if(todos.get(i).getId() == id){
                return todos.remove(i);
            }
        }

        return null;
    }

    @Override
    public Todo findById(int id) {
        for(int i =0; i<todos.size();i++){
            if(todos.get(i).getId() == id){
                return todos.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Todo> findAll() {
       // List<Todo> copyTodos = new ArrayList<>();
       // copyTodos.addAll(todos);
        //return copyTodos;

        return List.copyOf(todos);

    }
}

