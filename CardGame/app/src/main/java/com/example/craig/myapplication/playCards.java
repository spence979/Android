package com.example.craig.myapplication;

import android.app.Activity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class playCards extends Activity {
    private playCards ThisActivity;

    public boolean isSever = false;
    private int MyDeck = 0;
    private static DeckOfCards myDeckOfCards;
    private static String CurrentCard = null;
    private static String HostName;
    private TextView CardsInDeck;
    private ImageView PutDeck;
    private Button Start, SortButton;
    private TextView MyDeckText;

    public ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();
    public ArrayList<String> CurrentUsers = new ArrayList<String>();
    static ArrayList<Integer> SelectedCards = new ArrayList<Integer>();
    public static ArrayList<String> MyCurrentDeck = new ArrayList<String>();
    public static ArrayList<Integer> MyCurrentDeckButtons = new ArrayList<Integer>();
    public static String MyUsername;

    public static A_Chat_Client ChatClient;
    ServerReturn ServerLis;
    private LinearLayout CardRow;

    private ImageButton CardButton;
    private boolean TrickCard = false;
    private int CardPickUp = 1;

    //Threads
    ConnectToServer Connection;
    ServerThread Server;

    //Turn System
    private boolean MyTurn;
    private int CurentUserCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playcards);

        ImageView DealCard = (ImageView) findViewById(R.id.deck);
        PutDeck = (ImageView) findViewById(R.id.Putdeck);
        Start = (Button) findViewById(R.id.StartServer);
        SortButton = (Button) findViewById(R.id.btnSort);
        TextView ServerAddress = (TextView)findViewById(R.id.txtServer);
        MyDeckText = (TextView)findViewById(R.id.txtMyDeck);


        //Disable Screen Rotate
        android.provider.Settings.System.putInt(getContentResolver(),Settings.System.ACCELEROMETER_ROTATION, 0);
        MyUsername = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Intent intent = getIntent();

        CardRow = (LinearLayout)findViewById(R.id.MyCards);
        CardsInDeck = (TextView)findViewById(R.id.txtCardsInDeck);

        SortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortMenu(v);
            }
        });

        DealCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatClient.SEND("#?!-" + "GetCard-" + MyUsername);
            }
        });

        PutDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAddToDeck();
            }
        });

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstDeal();
                Start.setVisibility(View.GONE);
            }
        });

        Start.setVisibility(View.GONE);
        HostName = intent.getStringExtra("HOSTNAME");
        ServerAddress.setText(HostName);

        if(intent.getStringExtra("ACTION").equals("HOST")){
            myDeckOfCards = new DeckOfCards();

            isSever = true;
            Server = new ServerThread();
            Server.start();

            Connection = new ConnectToServer();
            Connection.start();
            Start.setVisibility(View.VISIBLE);
        }

        if(intent.getStringExtra("ACTION").equals("JOIN")){
            Connection = new ConnectToServer();
            Connection.start();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            ChatClient.DISCONNECT();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void choseDeck(View view){
           PopupMenu menu = new PopupMenu(this, view);
           menu.getMenuInflater().inflate(R.menu.carddesks, menu.getMenu());
           menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
               @Override
               public boolean onMenuItemClick(MenuItem item) {
                   Toast.makeText(getBaseContext(),item.toString(), Toast.LENGTH_LONG).show();
                   ChatClient.SEND("#?!-" + "SC-zero_of_" + item.toString().toLowerCase());
                   turnComplete();
                   return true;
               }
           });
           menu.show();
       }

    public void SortMenu(View view){
        PopupMenu menu = new PopupMenu(this, view);
        menu.getMenuInflater().inflate(R.menu.sort_deck, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int Index = 0; card_sort SortCards = new card_sort();
                ArrayList<String> MyCurrentDecTransformed = new ArrayList<String>();
                ArrayList<String> MyCurrentDec2= new ArrayList<String>();

                if(item.toString().equals("Number")){
                    for (String Card : MyCurrentDeck) {
                        MyCurrentDecTransformed.add(SortCards.SortByNumber(Card));
                        Index ++;
                    }

                    Collections.sort(MyCurrentDecTransformed, new Comparator<String>() {
                        public int compare(String a, String b) {
                            return Integer.signum(fixString(a) - fixString(b));
                        }
                        private int fixString(String in) {
                            return Integer.parseInt(in.substring(0, in.indexOf('_')));
                        }
                    });

                    for (String Card : MyCurrentDecTransformed) {
                        MyCurrentDec2.add(SortCards.SortBackByNumber(Card));
                        System.out.println(Card);
                    }
                }else if(item.toString().equals("Deck")){
                    for (String Card : MyCurrentDeck) {
                        MyCurrentDecTransformed.add(new StringBuffer(Card).reverse().toString());
                    }
                    Collections.sort(MyCurrentDecTransformed);

                    for (String Card : MyCurrentDecTransformed) {
                        MyCurrentDec2.add(new StringBuffer(Card).reverse().toString());
                        System.out.println(Card);
                    }
                }

                MyCurrentDeck.clear();
                MyDeck = 0;

                ViewGroup layout = (ViewGroup) findViewById(R.id.MyCards);
                ArrayList<Integer> MyCardButton = new ArrayList<Integer>(MyCurrentDeckButtons);

                MyCurrentDeckButtons.clear();

                //Remove All Buttons
                for (int Card : MyCardButton) {
                    View button = layout.findViewById(Card);
                    layout.removeView(button);
                }

                System.out.println("Sorted Array");
                for (String Card : MyCurrentDec2) {
                    SetCard(Card);
                }

                return true;
            }
        });
        menu.show();
    }

    public void FirstDeal(){
        myDeckOfCards.shuffle();

        for(int Players = 0; Players < CurrentUsers.size(); Players ++){
            for(int Cards = 0; Cards <= 7; Cards ++){
                String CardName = myDeckOfCards.dealCard().toString();
                ChatClient.SEND("#?!-" + "SetCard-" + CurrentUsers.get(Players) + "-" + CardName);
            }
        }

        String CardName = myDeckOfCards.dealCard().toString();
        //Set Deck Current Card
        ChatClient.SEND("#?!-" + "SC-" + CardName);
        //Set First User
        ChatClient.SEND("#?!-" + "TURN-" + CurrentUsers.get(0));
        //Echo Current Number Of Cards In Deck
        ChatClient.SEND("#?!-" + "CardsInDeck-" + (myDeckOfCards.CardDeck.size() - myDeckOfCards.currentCard));
    }

    public void DealCard(String Username){
            for (int i = 0; i < CardPickUp; i++) {
                try {
                    String CardName = myDeckOfCards.dealCard().toString();
                    ChatClient.SEND("#?!-" + "SetCard-" + Username + "-" + CardName);
                    ChatClient.SEND("#?!-" + "CardsInDeck-" + (myDeckOfCards.CardDeck.size() - myDeckOfCards.currentCard));

                    if (CurrentUsers.get(CurentUserCount).equals(Username)) {
                        NextTurn();
                    }
                } catch (Exception X) {
                    System.out.println(X.toString());
                }
            }
            CardPickUp = 1;
            TrickCard = false;

            ChatClient.SEND("#?!-" + "CardPickup-" + CardPickUp);
            ChatClient.SEND("#?!-" + "TrickCard-" + TrickCard);
    }

    public boolean CheckCards(int CardID){
        //Trick Cards i.e 8
        String CardNameData[] = getResources().getResourceEntryName(CardID).split("_");
        String CurrentCardData[] = getResources().getResourceEntryName(SelectedCards.get(0)).split("_");

        if(CurrentCardData[0].equals(CardNameData[0])) {
            return true;
        }
        return false;
    }

    public void SetCard(String Card){
        final String CardName = Card;
        final ImageButton CardButton = new ImageButton(getBaseContext());
        MyCurrentDeck.add(Card);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        MyDeck ++;
                        MyDeckText.setText("My Deck: " + MyDeck);

                        CardButton.setImageResource(getResources().getIdentifier(CardName, "drawable", getPackageName()));
                        CardButton.setBackgroundColor(Color.TRANSPARENT);


                        CardButton.setId(getResources().getIdentifier(CardName, "drawable", getPackageName()));
                        MyCurrentDeckButtons.add(getResources().getIdentifier(CardName, "drawable", getPackageName()));


                        CardButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                int CardID = CardButton.getId();

                                ColorDrawable buttonColor = (ColorDrawable) CardButton.getBackground();
                                int colorId = buttonColor.getColor();

                                if (colorId == -16776961) {
                                    CardButton.setBackgroundColor(0);
                                    SelectedCards.remove(SelectedCards.indexOf(CardID));
                                } else {
                                    if (SelectedCards.size() == 0 || CheckCards(CardID)) {
                                        CardButton.setBackgroundColor(Color.BLUE);
                                        SelectedCards.add(CardID);
                                    }
                                }
                            }
                        });
                        CardRow.addView(CardButton);
                    } catch (Exception X) {
                        System.out.println(X.getMessage());
                    }
                }
            });

    }

    public boolean CheckAddToDeck(){
        if(SelectedCards.size() == 0){
            return false;
        }

        String FirstCard[] = getResources().getResourceEntryName(SelectedCards.get(0)).split("_");
        String CurrentCardData[] = (CurrentCard).split("_");

        //2 & ACE of hearts
        if((CurrentCardData[0].equals("ace") &&  CurrentCardData[2].equals("hearts")) && TrickCard == true){
            if(FirstCard[0].equals("ace") && FirstCard[2].equals("spades")){
                AddToDeck();
                return true;
            }
            return true;
        }

        if(CurrentCardData[0].equals("two") && TrickCard == true){
            if(FirstCard[0].equals("two")) {
                AddToDeck();
                return true;
            }
            return true;
        }

        //Trick Card 8
        if((!CurrentCardData[0].equals("ace") &&  !CurrentCardData[2].equals("hearts")) || !CurrentCardData[0].equals("two")){
            if(FirstCard[0].equals("eight")) {
                AddToDeck();
                return true;
            }
        }

        //Same Number
        if(CurrentCardData[0].equals(FirstCard[0])) {
            AddToDeck();
            return true;
        }

        //Same Suit
        if(CurrentCardData[2].equals(FirstCard[2])) {
            AddToDeck();
            return true;
        }
        return false;
    }

    public void AddToDeck(){
        //changeCurrentCard();
        if(MyTurn) {
            if(SelectedCards.size() == 0){
                Toast.makeText(getBaseContext(), "No Cards Detected", Toast.LENGTH_SHORT).show();
                return;
            }

            ChatClient.SEND("#?!-" + "SC-" + getResources().getResourceEntryName(SelectedCards.get(SelectedCards.size() - 1)));
            //Remove Cards From Players Deck
            ViewGroup layout = (ViewGroup) findViewById(R.id.MyCards);

            for (int Card : SelectedCards) {
                MyDeck --;
                MyDeckText.setText("My Deck: " + MyDeck);

                if(MyDeck == 0){
                    ChatClient.SEND("#?!-" + "CD-" + MyUsername);
                }
                try {
                    View command = layout.findViewById(Card);
                    layout.removeView(command);

                    MyCurrentDeckButtons.remove(MyCurrentDeckButtons.indexOf(Card));
                    MyCurrentDeck.remove(MyCurrentDeck.indexOf(getResources().getResourceEntryName(Card)));

                    String CardName = getResources().getResourceEntryName(Card);
                    String CurrentCardData[] = (CardName).split("_");

                    if(CurrentCardData[0].equals("two") || (CurrentCardData[0].equals("ace")) || (CurrentCardData[0].equals("jack")) || (CurrentCardData[0].equals("queen"))){
                        ChatClient.SEND("#?!-" + "TRICK-" + getResources().getResourceEntryName(Card));
                    }
                    DeckOfCards.UsedCards.add(getResources().getResourceEntryName(Card));
                } catch (Exception X) {
                    System.out.println(X.getMessage());
                }
            }

            String CardName = getResources().getResourceEntryName(SelectedCards.get(SelectedCards.size() - 1));
            String CurrentCardData[] = (CardName).split("_");
            if(CurrentCardData[0].equals("eight")){
                choseDeck(PutDeck);
            }else{
                turnComplete();
            }
            SelectedCards.clear();
        }else{
            Toast.makeText(getBaseContext(), "Two For Mistake", Toast.LENGTH_LONG).show();
            ChatClient.SEND("#?!-" + "GetCard-" + MyUsername);
            ChatClient.SEND("#?!-" + "GetCard-" + MyUsername);
        }
    }

    public void turnComplete(){
        ChatClient.SEND("#?!-" + "TURN_Complete");
    }

    public void NextTurn(){
        try {
            if(CurrentUsers.size() == 1){
                CurentUserCount = 0;
            }else if (CurentUserCount < (CurrentUsers.size()- 1) && CurrentUsers.size() != 0) {
                CurentUserCount++;
            } else {
                CurentUserCount = 0;
            }
            ChatClient.SEND("#?!-" + "TURN-" + CurrentUsers.get(CurentUserCount));
        }catch(Exception X){
            System.out.println(X.getMessage());
        }
    }

    public void UpdateTurn(String CurrentUserCount){
        if(CurrentUserCount.equals(MyUsername)){
            MyTurn = true;
            ShowMessage("Your Turn");
        }else{
            MyTurn = false;
        }
    }

    private void ShowMessage(final String Message){
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(getBaseContext(), Message, Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void UpdateCardsInDeck(final int Number){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CardsInDeck.setText("Cards : " + Number);
            }
        });
    }

    private void TrickCardSetup(String Card){
        String CurrentCardData[] = (Card).split("_");
        if(CurrentCardData[0].equals("queen")){
            Collections.reverse(Arrays.asList(CurrentUsers));
            NextTurn();
        }

        if(CurrentCardData[0].equals("jack")){
            NextTurn();
        }

        if(CurrentCardData[0].equals("two")){
            TrickCard = true;
            if(CardPickUp == 1){
                CardPickUp = 2;
            }else{
                CardPickUp += 2;
            }
        }

        if(CurrentCardData[0].equals("ace") && CurrentCardData[2].equals("hearts")){
            TrickCard = true;
            if(CardPickUp == 1){
                CardPickUp = 5;
            }else{
                CardPickUp += 2;
            }
        }

        if(CurrentCardData[0].equals("ace") && CurrentCardData[2].equals("spades")){
            TrickCard = false;
            CardPickUp = 1;
        }
        ChatClient.SEND("#?!-" + "CardPickup-" + CardPickUp);
        ChatClient.SEND("#?!-" + "TrickCard-" + TrickCard);
    }

    public void UpdateCardPickup(final int Number){
        final TextView CardPickup = (TextView)findViewById(R.id.txtCardPickup);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    CardPickup.setText("Card Pickup: " + Number);
                }catch (Exception X){
                    System.out.println(X.toString());
                }
            }
        });
    }

    public void changeCurrentCard(final String CardName){
        final ImageView CurrentCardImage = (ImageView) findViewById(R.id.Putdeck);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    CurrentCardImage.setImageResource(getResources().getIdentifier(CardName, "drawable", getPackageName()));
                    CurrentCard = getResources().getResourceEntryName(getResources().getIdentifier(CardName, "drawable", getPackageName()));
                }catch (Exception X){
                    System.out.println(X.toString());
                }
            }
        });
    }

    private void ClearedDeck(String Username){

    }
    //SERVER
    public class ServerThread extends Thread {
        Socket SOCK;
        @Override
        public void run() {
            try {
                final int PORT = 1245;
                ServerSocket SERVER = new ServerSocket(PORT);
                while (true) {
                    SOCK = SERVER.accept();

                    ConnectionArray.add(SOCK);
                    AddUserName(SOCK);

                    ServerReturn ServerLis = new ServerReturn(SOCK);
                    ServerLis.start();
                }
            } catch (Exception X) {
                System.out.print(X.getMessage());
            }
        }


        public void AddUserName(Socket X) throws IOException {
            Scanner INPUT = new Scanner(X.getInputStream());

            String UserName = INPUT.nextLine();
            CurrentUsers.add(UserName);

            //Start.setEnabled(true);

            for (int i = 1; i <= ConnectionArray.size(); i++) {
                Socket TEMP_SOCK = (Socket) ConnectionArray.get(i - 1);
                PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                OUT.println("#?!" + CurrentUsers);
                OUT.flush();
            }
        }
    }
    //SERVER Listener
    public class ServerReturn extends Thread {
        private Socket SOCK;
        private Scanner INPUT;
        private PrintWriter OUT;
        private String MESSAGE = "";

        public ServerReturn(Socket X){
            this.SOCK = X;
        }

        @Override
        public void run() {
            try {
                try {
                    INPUT = new Scanner(SOCK.getInputStream());
                    OUT = new PrintWriter(SOCK.getOutputStream());
                    while (true) {
                        CheckConnection();

                        if (!INPUT.hasNext()) {
                            return;
                        }

                        MESSAGE = INPUT.nextLine();

                        for (int i = 1; i <= ConnectionArray.size(); i++) {
                            Socket TEMP_SOCK = (Socket) ConnectionArray.get(i - 1);
                            PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                            TEMP_OUT.println(MESSAGE);
                            TEMP_OUT.flush();
                        }
                    }
                } finally {
                    SOCK.close();
                }
            } catch (Exception X) {
                System.out.print(X.getMessage());
            }
        }

        public void CheckConnection() throws IOException {
            if (!SOCK.isConnected()) {
                System.out.println("Disconnected");
                for (int i = 1; i <= ConnectionArray.size(); i++) {
                    if (ConnectionArray.get(i) == SOCK) {
                        ConnectionArray.remove(i);
                    }
                }

                for (int i = 1; i <= ConnectionArray.size(); i++) {
                    Socket TEMP_SOCK = (Socket) ConnectionArray.get(i - 1);
                    PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println(TEMP_SOCK.getLocalAddress().getHostName() + " Disconnected");
                    TEMP_OUT.flush();

                    System.out.println(TEMP_SOCK.getLocalAddress().getHostName() + " Disconnected");
                }
            }
        }
    }

    public class ConnectToServer extends Thread{
        public void run() {
            try {
                int PORT = 1245;
                final String HOST = HostName;

                Socket SOCK = new Socket(HOST, PORT);

                ShowMessage("You Connected To : " + HOST);

                ChatClient = new A_Chat_Client(SOCK);
                PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());

                OUT.println(MyUsername);
                OUT.flush();

                Thread X = new Thread(ChatClient);
                X.start();

            } catch (Exception X) {
                System.out.print(X);

                Intent MainMenu;

                if(isSever){
                    MainMenu = new Intent(getBaseContext(), main_Menu.class);
                    Server.interrupt();
                    if(ServerLis!= null && ServerLis.isAlive()) {
                        ServerLis.interrupt();
                    }
                }else{
                    MainMenu = new Intent(getBaseContext(), join_game.class);
                }
                startActivity(MainMenu);
                Connection.interrupt();
                finish();
            }
        }
    }

    public class A_Chat_Client implements Runnable {

        Socket SOCK;
        Scanner INPUT;
        Scanner SEND = new Scanner(System.in);
        PrintWriter OUT;


        public A_Chat_Client(Socket X){
            this.SOCK = X;
        }
        @Override
        public void run() {
            try{
                try{
                    INPUT = new Scanner(SOCK.getInputStream());
                    OUT = new PrintWriter(SOCK.getOutputStream());
                    OUT.flush();

                    CheckStream();

                }finally{
                    SOCK.close();
                }
            }catch(Exception X){
                System.out.print(X);
            }
        }

        public void DISCONNECT() throws IOException{
            OUT.println("Has Disconnected");
            OUT.flush();
            SOCK.close();
            System.exit(0);
        }

        public void CheckStream(){
            while(true){
                RECEIEVE();
            }
        }

        public void RECEIEVE(){
            if(INPUT.hasNext()){
                String MESSAGE = INPUT.nextLine();
                if(MESSAGE.contains("#?!")){
                    String TEMP1 = MESSAGE.substring(4);
                    String TEMP2[] = TEMP1.split("-");

                    if(TEMP2[0].equals("SetCard")){
                        if(playCards.MyUsername.equals(TEMP2[1])){
                            SetCard(TEMP2[2]);
                        }
                    }

                    if(TEMP2[0].equals("GetCard")){
                        if(isSever) {
                            DealCard(TEMP2[1]);
                        }
                    }

                    if(TEMP2[0].equals("SC")){
                        changeCurrentCard(TEMP2[1]);
                    }

                    if(TEMP2[0].equals("TURN")){
                        UpdateTurn((TEMP2[1]));
                    }

                    if(TEMP2[0].equals("CardsInDeck")){
                        UpdateCardsInDeck(parseInt(TEMP2[1]));
                    }
                    if(TEMP2[0].equals("TRICK")){
                        if(isSever) {
                            TrickCardSetup((TEMP2[1]));
                        }
                    }

                    if(TEMP2[0].equals("TrickCard")){
                        TrickCard = Boolean.parseBoolean(TEMP2[1]);
                    }
                    if(TEMP2[0].equals("CardPickup")){
                        UpdateCardPickup(parseInt(TEMP2[1]));
                        CardPickUp = parseInt(TEMP2[1]);
                    }

                    if(TEMP2[0].equals("TURN_Complete")){
                        if(isSever) {
                            NextTurn();
                        }
                    }

                    if(TEMP2[0].equals("CD")){
                        if(isSever) {
                            ClearedDeck(TEMP2[1]);
                        }
                    }
                }else{
                    System.out.println("SOMEONE SAID" + MESSAGE);
                }
            }
        }

        public void SEND(String X){
            OUT.println(X);
            OUT.flush();
        }
    }
}