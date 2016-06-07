package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.Models.Actor;
import sample.Models.Perfomance;
import sample.Models.Theatre;
import sample.Models.User;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;

/**
 * Created by Adm on 27.03.2016.
 */
public class Controller {


    public TableView<Show> tableSh;
    public TableColumn<Show, Integer> tabShFS;
    public TableColumn<Show, String> tabShTi;
    public TableColumn<Show, String> tabShPe;
    public TableColumn<Show, String> tabShTh;
    public TableColumn<Show, Integer> tabShId;

    public TableView<Employment> tableEm;
    public TableColumn<Employment, String> tabEmFio;
    public TableColumn<Employment, String> tabEmPe;
    public TableColumn<Employment, String> tabEmGe;
    public TableColumn<Employment, Integer> tabEmCo;

    public TableView<User> tableUs;
    public TableColumn<User, Integer> tabUsId;
    public TableColumn<User, String> tabUsLog;
    public TableColumn<User, String> tabUsPa;
    public TableColumn<User, String> tabUsTh;
    public TableColumn<User, String> tabUsRo;

    public TableView<Actor> tableAc;
    public TableColumn<Actor, Integer> tabAcId;
    public TableColumn<Actor, String> tabAcFio;
    public TableColumn<Actor, Integer> tabAcY;
    public TableColumn<Actor, Integer> tabAcEx;
    public TableColumn<Actor, Integer> tabAcCo;
    public TableColumn<Actor, String> tabAcRa;
    public TableColumn<Actor, String> tabAcGe;
    public TableColumn<Actor, String> tabAcAd;
    public TableColumn<Actor, Integer> tabAcUs;

    public TextField loginF;
    public TextField passF;
    public Button Enter;
    public Label Logged;
    public TabPane dddd;
    public Button Register;

    public Tab HideUsers;
    public Tab HideActors;
    public Tab HidePerf;
    public Tab HideEmpl;
    public Tab HideShow;
    public Tab HideTheatre;
    public Tab HideCreators;



    public TableView<Theatre> tableTh;
    public TableColumn<Theatre, Integer> tabThId;
    public TableColumn<Theatre, String> tabThNa;
    public TableColumn<Theatre, String> tabThAd;
    public TableColumn<Theatre, String> tabThTe;
    public TableColumn<Theatre, String> tabThTy;


    public TableView<Creator> tableCr;
    public TableColumn<Creator, Integer> tabCrId;
    public TableColumn<Creator, String> tabCrNa;
    public TableColumn<Creator, String> tabCrPo;

    public Label Signal;
    public Button Delete;


    private ObservableList<Perfomance> data2 = FXCollections.observableArrayList();
    private ObservableList<Employment> listEmp = FXCollections.observableArrayList();
    private ObservableList<Show> listShow = FXCollections.observableArrayList();
    private ObservableList<User> listUsers = FXCollections.observableArrayList();
    private ObservableList<Actor> listActors = FXCollections.observableArrayList();
    private ObservableList<Theatre> listTheatres = FXCollections.observableArrayList();
    private ObservableList<Creator> listCreators = FXCollections.observableArrayList();
    private ObservableList<CreatorList> listCreatorsList = FXCollections.observableArrayList();

    private static Connection c;
    public static Statement st;
    private static FXMLLoader loader;
    private Scene scene;
    private Stage stage;


    @FXML private TableView<Perfomance> table;
    @FXML private TableColumn<Perfomance,Integer> tabId;
    @FXML private TableColumn<Perfomance,String> tabname;
    @FXML private TableColumn<Perfomance,String> tabY;
    @FXML private TableColumn<Perfomance,Integer> tabBu;
    @FXML private TableColumn<Perfomance,Date> tabBe;
    @FXML private TableColumn<Perfomance,Date> tabEn;
    @FXML private TableColumn<Perfomance,String> tabG;
    @FXML private TableColumn<Perfomance,Integer> tabI;
    @FXML private Button enterButton;
    @FXML private Button createButton;
    private String ControllerUserRole;
    private String LoggedUserID;


    @FXML
    public void initialize() throws Exception {
        setDB();

        Delete.setDisable(true);
        enterButton.setDisable(true);
        createButton.setDisable(true);
        HidePerf.setDisable(true);
        HideUsers.setDisable(true);
        HideActors.setDisable(true);
        HideEmpl.setDisable(true);
        HideShow.setDisable(true);
        HideUsers.setDisable(true);
        HideTheatre.setDisable(true);
        HideCreators.setDisable(true);
        Logged.textProperty().addListener((observable1, oldValue1, newValue1) -> {
            System.out.println(ControllerUserRole);
            if (ControllerUserRole.equals("Admin")){
                Delete.setDisable(false);
                enterButton.setDisable(false);
                createButton.setDisable(false);
                HidePerf.setDisable(false);
                HideUsers.setDisable(false);
                HideActors.setDisable(false);
                HideEmpl.setDisable(false);
                HideShow.setDisable(false);
                HideUsers.setDisable(false);
                HideTheatre.setDisable(false);
                HideCreators.setDisable(false);
                String SQL = "SELECT PERFOMANCEID,NAME,YEAR,BUDGET,BEGINTIME,ENDTIME,GENRE,INCOME FROM PERFOMANCE,INCOME WHERE PERFOMANCE.INCOMEID=INCOME.INCOMEID";
                try {
                    ResultSet rs = st.executeQuery(SQL);
                    while (rs.next()){
                        data2.add(new Perfomance(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),
                                rs.getDate(5),rs.getDate(6),rs.getString(7),rs.getInt(8)));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                SetPerfomance();
            }else if (ControllerUserRole.equals("Actor")){
                HideActors.setDisable(false);
                HidePerf.setDisable(false);
                String SQL = "SELECT PERFOMANCEID,NAME,YEAR,BUDGET,BEGINTIME,ENDTIME,GENRE,INCOME FROM PERFOMANCE,INCOME WHERE PERFOMANCE.INCOMEID=INCOME.INCOMEID AND PERFOMANCE.PERFOMANCEID IN(SELECT EMPLOYMENT.PERFOMANCEID FROM EMPLOYMENT WHERE EMPLOYMENT.ACTORID IN (SELECT ACTOR.ACTORID FROM ACTOR WHERE ACTOR.USERID IN(SELECT USERS.USERID FROM USERS WHERE USERS.LOGIN LIKE ('" + Logged.getText() + "')))) ORDER BY PERFOMANCEID";
                try {
                    ResultSet rs = st.executeQuery(SQL);
                    while (rs.next()){
                        data2.add(new Perfomance(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),
                                rs.getDate(5),rs.getDate(6),rs.getString(7),rs.getInt(8)));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                SetPerfomance();
            }else if(ControllerUserRole.equals("Creator")){
                tableAc.setEditable(false);
                enterButton.setDisable(false);
                createButton.setDisable(false);
                HideActors.setDisable(false);
                HidePerf.setDisable(false);
                HideEmpl.setDisable(false);
                HideShow.setDisable(false);
                String SQL = "SELECT PERFOMANCEID,NAME,YEAR,BUDGET,BEGINTIME,ENDTIME,GENRE,INCOME FROM PERFOMANCE,INCOME WHERE PERFOMANCE.INCOMEID=INCOME.INCOMEID";
                try {
                    ResultSet rs = st.executeQuery(SQL);
                    while (rs.next()){
                        data2.add(new Perfomance(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),
                                rs.getDate(5),rs.getDate(6),rs.getString(7),rs.getInt(8)));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                SetPerfomance();
            }
        });


        HideUsers.setDisable(true);

        QuerySelect();

        SetActor();
        dddd.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue.getText());
        });
        SetShow();

        SetUser();

        SetCreatorsList();

        SetTheatre();

        SetCreators();

        SetEmployment();

        Logged.textProperty().addListener((observable1, oldValue1, newValue1) -> {
            try {
                SetUser();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Delete.setOnAction(event1 -> {
            Platform.runLater(() -> {
                if (dddd.getSelectionModel().getSelectedItem().getText().equals("Создатели")) {
                    String Sql = "DELETE FROM CREATOR WHERE CREATORID=" + tableCr.getSelectionModel().getSelectedItem().getCreatorid();
                    try {
                        st.executeUpdate(Sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else if (dddd.getSelectionModel().getSelectedItem().getText().equals("Пользователи")){
                    String Sql = "DELETE FROM USERS WHERE USERID=" + tableUs.getSelectionModel().getSelectedItem().getUserid();
                    try {
                        st.executeUpdate(Sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else if (dddd.getSelectionModel().getSelectedItem().getText().equals("Показ")){
                    String Sql = "DELETE FROM SHOW WHERE SHOWID=" + tableSh.getSelectionModel().getSelectedItem().getShowid();
                    try {
                        st.executeUpdate(Sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else if (dddd.getSelectionModel().getSelectedItem().getText().equals("Представление")){
                    /*try {
                    String Sql = "DELETE FROM SHOW WHERE PERFOMANCEID=" + table.getSelectionModel().getSelectedItem().getId();
                    st.executeUpdate(Sql);
                    Sql = "DELETE FROM EMPLOYMENT WHERE PERFOMANCEID=" + table.getSelectionModel().getSelectedItem().getId();
                    st.executeUpdate(Sql);
                    Sql = "DELETE FROM PERFOMANCE WHERE PERFOMANCEID=" + table.getSelectionModel().getSelectedItem().getId();
                    st.executeUpdate(Sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }*/
                    try {
                        String Sql = "DELETE FROM PERFOMANCE WHERE PERFOMANCEID=" + table.getSelectionModel().getSelectedItem().getId();
                        st.executeUpdate(Sql);
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Вы пытаетесь удалить активное представление!");
                        alert.showAndWait();
                        e.printStackTrace();
                    }
                }
            });
        });
        enterButton.setOnAction(event -> {
            int i=0;
            while (i<data2.size()) {
                try {
                    /*String SQL2 = "UPDATE PERFOMANCE SET NAME='" + data2.get(i).getName()
                            + "',YEAR='" + data2.get(i).getYear()
                            + "',BUDGET=" + data2.get(i).getBudget()
                            + ",BEGINTIME=TO_DATE('" + data2.get(i).getBeginTime()
                            + "', 'yyyy/mm/dd'),ENDTIME=TO_DATE('" + data2.get(i).getEndTime()
                            + "', 'yyyy/mm/dd'),GENRE='" + data2.get(i).getGenre()
                            + "' WHERE PERFOMANCEID=" + data2.get(i).getId();
                    st.executeUpdate(SQL2);*/

                    String SQL2 = "UPDATE INCOME SET INCOME=" + data2.get(i).getIncome() + " WHERE INCOMEID=" + data2.get(i).getId();
                    st.executeUpdate(SQL2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(data2.get(i).getIncome());
                i++;
            }
            i=0;
            while (i<listUsers.size()) {
                try {
                    String SQL2 ="SELECT THEATREID FROM THEATRE WHERE NAME LIKE ('" + listUsers.get(i).getTheatreid() + "')";
                    ResultSet rs = st.executeQuery(SQL2);

                    rs.next();
                    SQL2 = "UPDATE USERS SET LOGIN='" + listUsers.get(i).getLogin()
                            + "',PASSWORD='" + listUsers.get(i).getPassword()
                            + "',THEATREID='" + rs.getString("THEATREID")
                            + "'WHERE USERID =" + listUsers.get(i).getUserid();

                    st.executeUpdate(SQL2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                i++;
            }
            i=0;
            while (i<listCreators.size()) {
                try {
                    String SQL2 ="SELECT POSITIONID FROM POSITION WHERE NAME LIKE ('" + listCreators.get(i).getPositionid() + "')";
                    ResultSet rs = st.executeQuery(SQL2);

                    rs.next();
                    SQL2 = "UPDATE CREATOR SET FIO='" + listCreators.get(i).getFio()
                            + "',POSITIONID=" + rs.getString("POSITIONID")
                            + " WHERE CREATORID=" + listCreators.get(i).getCreatorid();

                    st.executeUpdate(SQL2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                i++;
            }
            i=0;
            while (i<listTheatres.size()) {
                try {
                    String SQL2 ="SELECT TYPEID FROM TYPE WHERE TYPE LIKE ('" + listTheatres.get(i).getType() + "')";
                    ResultSet rs = st.executeQuery(SQL2);

                    rs.next();
                    SQL2 = "UPDATE THEATRE SET NAME='" + listTheatres.get(i).getName()
                            + "',ADRESS='" + listTheatres.get(i).getAddress()
                            + "',TELEPHONE=" + listTheatres.get(i).getTelephone()
                            + ",TYPEID=" + rs.getInt("TYPEID")
                            + " WHERE THEATREID=" + listTheatres.get(i).getTheatreid();

                    st.executeUpdate(SQL2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                i++;
            }

        });

        Signal.textProperty().addListener((observable1, oldValue1, newValue1) -> {
            try {
                SetUser();
                SetCreators();
                SetTheatre();
                SetEmployment();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        createButton.setOnAction(event -> {
            Platform.runLater(() -> {
                if(dddd.getSelectionModel().getSelectedItem().getText().equals("Представление")){
                    try {
                        loader = new FXMLLoader(getClass().getResource("Views\\CreatePerfomance.fxml"));
                        scene = new Scene((Parent) loader.load());
                        PerfomanceCreateController controller = loader.<PerfomanceCreateController>getController();
                        controller.initializeF(this, scene,Signal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }else if (dddd.getSelectionModel().getSelectedItem().getText().equals("Актеры")){
                    try {
                        loader = new FXMLLoader(getClass().getResource("Views\\CreateActor.fxml"));
                        scene = new Scene((Parent) loader.load());
                        ActorCreateController controller = loader.<ActorCreateController>getController();
                        controller.initializeF(this, scene,Signal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }else if (dddd.getSelectionModel().getSelectedItem().getText().equals("Занятость")){
                    try {
                        loader = new FXMLLoader(getClass().getResource("Views\\CreateEmployment.fxml"));
                        scene = new Scene((Parent) loader.load());
                        EmploymentCreate controller = loader.<EmploymentCreate>getController();
                        controller.initializeF(this, scene,Signal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }else if (dddd.getSelectionModel().getSelectedItem().getText().equals("Театры")){
                    try {
                        loader = new FXMLLoader(getClass().getResource("Views\\CreateTheatre.fxml"));
                        scene = new Scene((Parent) loader.load());
                        TheatreCreate controller = loader.<TheatreCreate>getController();
                        controller.initializeF(this, scene,Signal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }else if (dddd.getSelectionModel().getSelectedItem().getText().equals("Создатели")){
                    try {
                        loader = new FXMLLoader(getClass().getResource("Views\\CreateCreator.fxml"));
                        scene = new Scene((Parent) loader.load());
                        CreatorCreate controller = loader.<CreatorCreate>getController();
                        controller.initializeF(this, scene,Signal);
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                    stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }else if (dddd.getSelectionModel().getSelectedItem().getText().equals("Показ")){
                    try {
                        loader = new FXMLLoader(getClass().getResource("Views\\CreateShow.fxml"));
                        scene = new Scene((Parent) loader.load());
                        ShowCreate controller = loader.<ShowCreate>getController();
                        controller.initializeF(this, scene,Signal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }
            });
        });

        Enter.setOnAction(event -> {
            try {
                String SQL = "SELECT LOGIN,PASSWORD, ROLE, FIO, ACTORID FROM USERS, ACTOR WHERE USERS.USERID = ACTOR.USERID AND LOGIN LIKE ('" + loginF.getText() + "') AND  USERS.PASSWORD LIKE ('" + passF.getText() + "')";
                ResultSet rs=st.executeQuery(SQL);
                if (!rs.next()) {
                    SQL = "SELECT LOGIN,PASSWORD, ROLE FROM USERS WHERE LOGIN LIKE ('" + loginF.getText() + "') AND  USERS.PASSWORD LIKE ('" + passF.getText() + "')";
                    rs=st.executeQuery(SQL);
                    if (rs.next()){
                        System.out.println("USERUSERUSER!!!");
                        if (rs.getString("ROLE")!=null){
                            ControllerUserRole=rs.getString("ROLE");
                        }else {
                            ControllerUserRole="1";
                        }
                        Logged.setText(loginF.getText());
                        loginF.setVisible(false);
                        passF.setVisible(false);
                        Enter.setVisible(false);
                        Register.setVisible(false);
                    }else {

                    }
                } else {
                    System.out.println("USERUSERUSER!!!");
                    if (rs.getString("ROLE")!=null){
                        LoggedUserID=rs.getString("ACTORID");
                        ControllerUserRole=rs.getString("ROLE");
                    }else {

                        ControllerUserRole="1";
                    }
                    Logged.setText(loginF.getText());
                    loginF.setVisible(false);
                    passF.setVisible(false);
                    Enter.setVisible(false);
                    Register.setVisible(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Register.setOnAction(event -> {
            Platform.runLater(() ->{
                try {
                    loader = new FXMLLoader(getClass().getResource("Views\\CreateUser.fxml"));
                    scene = new Scene((Parent) loader.load());
                    UserCreate controller = loader.<UserCreate>getController();
                    controller.initializeF(this, scene,Signal);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                stage = new Stage();
                stage.setScene(scene);
                stage.show();
            });
        });
    }

    private void QuerySelect() throws SQLException {

    }

    private void SetCreators() throws SQLException {
        listCreators.clear();
        String SQL = "SELECT CREATORID, FIO, NAME FROM CREATOR,POSITION WHERE CREATOR.POSITIONID=POSITION.POSITIONID";
        ResultSet rs = st.executeQuery(SQL);

        while (rs.next()){
            listCreators.add(new Creator(rs.getInt(1),rs.getString(2),rs.getString(3)));
        }

        tableCr.setEditable(true);
        tabCrId.setCellValueFactory(new PropertyValueFactory<Creator, Integer>("Creatorid"));
        tabCrNa.setCellValueFactory(new PropertyValueFactory<Creator, String>("Fio"));
        tabCrPo.setCellValueFactory(new PropertyValueFactory<Creator, String>("Positionid"));

        /*tabCrId.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabCrId.setOnEditCommit(
                t -> ((Creator) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setCreatorid(t.getNewValue())
        );
        tabCrNa.setCellFactory(TextFieldTableCell.forTableColumn());
        tabCrNa.setOnEditCommit(
                t -> ((Creator) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setFio(t.getNewValue())
        );
        tabCrPo.setCellFactory(TextFieldTableCell.forTableColumn());
        tabCrPo.setOnEditCommit(
                t -> ((Creator) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setPositionid(t.getNewValue())
        );*/
        tableCr.setItems(listCreators);
    }

    private void SetCreatorsList() throws SQLException {
        listCreators.clear();
        String SQL = "SELECT FIO, PERFOMANCE.NAME, POSITION.NAME FROM CREATOR,PERFOMANCE,CREATORSLIST,POSITION WHERE CREATOR.CREATORID=CREATORSLIST.CREATORID AND PERFOMANCE.PERFOMANCEID = CREATORSLIST.PERFOMANCEID AND POSITION.POSITIONID = CREATOR.POSITIONID";
        ResultSet rs = st.executeQuery(SQL);

        while (rs.next()){
            listCreatorsList.add(new CreatorList(rs.getString(1),rs.getString(2),rs.getString(3)));
        }

        /*tableCr.setEditable(true);
        tabCrId.setCellValueFactory(new PropertyValueFactory<Creator, Integer>("Creatorid"));
        tabCrNa.setCellValueFactory(new PropertyValueFactory<Creator, String>("Fio"));
        tabCrPo.setCellValueFactory(new PropertyValueFactory<Creator, String>("Positionid"));

        tabCrId.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabCrId.setOnEditCommit(
                t -> ((Creator) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setCreatorid(t.getNewValue())
        );
        tabCrNa.setCellFactory(TextFieldTableCell.forTableColumn());
        tabCrNa.setOnEditCommit(
                t -> ((Creator) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setFio(t.getNewValue())
        );
        tabCrPo.setCellFactory(TextFieldTableCell.forTableColumn());
        tabCrPo.setOnEditCommit(
                t -> ((Creator) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setPositionid(t.getNewValue())
        );
        tableCr.setItems(listCreators);*/
    }


    private void SetTheatre() throws SQLException {

        String SQL = "SELECT THEATREID, NAME, ADRESS, TELEPHONE,TYPE.TYPE FROM THEATRE,TYPE WHERE THEATRE.TYPEID=TYPE.TYPEID";
        ResultSet rs = st.executeQuery(SQL);

        while (rs.next()){
            listTheatres.add(new Theatre(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
        }

        tableTh.setEditable(true);
        tabThId.setCellValueFactory(new PropertyValueFactory<Theatre, Integer>("Theatreid"));
        tabThNa.setCellValueFactory(new PropertyValueFactory<Theatre, String>("Name"));
        tabThAd.setCellValueFactory(new PropertyValueFactory<Theatre, String>("Address"));
        tabThTe.setCellValueFactory(new PropertyValueFactory<Theatre, String>("Telephone"));
        tabThTy.setCellValueFactory(new PropertyValueFactory<Theatre, String>("Type"));

        tabThId.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabThId.setOnEditCommit(
                t -> ((Theatre) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setTheatreid(t.getNewValue())
        );
        tabThNa.setCellFactory(TextFieldTableCell.forTableColumn());
        tabThNa.setOnEditCommit(
                t -> ((Theatre) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
        );
        tabThAd.setCellFactory(TextFieldTableCell.forTableColumn());
        tabThAd.setOnEditCommit(
                t -> ((Theatre) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setAddress(t.getNewValue())
        );
        tabThTe.setCellFactory(TextFieldTableCell.forTableColumn());
        tabThTe.setOnEditCommit(
                t -> ((Theatre) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setTelephone(t.getNewValue())
        );
        tabThTy.setCellFactory(TextFieldTableCell.forTableColumn());
        tabThTy.setOnEditCommit(
                t -> ((Theatre) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setType(t.getNewValue())
        );
        tableTh.setItems(listTheatres);
    }

    private void SetShow() throws SQLException {
        listShow.clear();
        String SQL = "SELECT THEATRE.NAME, PERFOMANCE.NAME, TIMET, SHOWID, FREESEATS FROM SHOW,THEATRE,PERFOMANCE WHERE THEATRE.THEATREID=SHOW.THEATREID AND PERFOMANCE.PERFOMANCEID=SHOW.PERFOMANCEID";
        ResultSet rs = st.executeQuery(SQL);

        while (rs.next()){
            listShow.add(new Show(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)));
        }

        tableSh.setEditable(true);
        tabShTh.setCellValueFactory(new PropertyValueFactory<Show, String>("Theatreid"));
        tabShPe.setCellValueFactory(new PropertyValueFactory<Show, String>("Perfomanceid"));
        tabShTi.setCellValueFactory(new PropertyValueFactory<Show, String>("Time"));
        tabShId.setCellValueFactory(new PropertyValueFactory<Show, Integer>("Showid"));
        tabShFS.setCellValueFactory(new PropertyValueFactory<Show, Integer>("Freeseats"));


        tableSh.setItems(listShow);
    }

    private void SetEmployment() throws SQLException {

        String SQL = "SELECT FIO, NAME, EMPLOYMENT.ROLE, CONTRACT FROM EMPLOYMENT,ACTOR,PERFOMANCE WHERE EMPLOYMENT.ACTORID=ACTOR.ACTORID AND PERFOMANCE.PERFOMANCEID=EMPLOYMENT.PERFOMANCEID";
        ResultSet rs = st.executeQuery(SQL);

        while (rs.next()){
            listEmp.add(new Employment(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
        }

        tableEm.setEditable(true);
        tabEmFio.setCellValueFactory(new PropertyValueFactory<Employment, String>("Actorid"));
        tabEmPe.setCellValueFactory(new PropertyValueFactory<Employment, String>("Perfomanceid"));
        tabEmGe.setCellValueFactory(new PropertyValueFactory<Employment, String>("Role"));
        tabEmCo.setCellValueFactory(new PropertyValueFactory<Employment, Integer>("Contract"));

        /*tabEmFio.setCellFactory(TextFieldTableCell.forTableColumn());
        tabEmFio.setOnEditCommit(
                t -> ((Employment) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setActorid(t.getNewValue())
        );
        tabEmPe.setCellFactory(TextFieldTableCell.forTableColumn());
        tabEmPe.setOnEditCommit(
                t -> ((Employment) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setPerfomanceid(t.getNewValue())
        );

        tabEmGe.setCellFactory(TextFieldTableCell.forTableColumn());
        tabEmGe.setOnEditCommit(
                t -> ((Employment) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setRole(t.getNewValue())
        );
        tabEmCo.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabEmCo.setOnEditCommit(
                t -> ((Employment) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setContract(t.getNewValue())
        );*/
        tableEm.setItems(listEmp);
    }

    public void SetUser() throws SQLException {
        listUsers.clear();
        String SQL = "SELECT USERID, LOGIN, PASSWORD, NAME, ROLE FROM USERS,THEATRE WHERE THEATRE.THEATREID=USERS.THEATREID ORDER BY USERID";
        ResultSet rs = st.executeQuery(SQL);

        while (rs.next()){
            listUsers.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
        }


        tabUsId.setCellValueFactory(new PropertyValueFactory<User, Integer>("Userid"));
        tabUsLog.setCellValueFactory(new PropertyValueFactory<User, String>("Login"));
        tabUsPa.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
        tabUsTh.setCellValueFactory(new PropertyValueFactory<User, String>("Theatreid"));
        tabUsRo.setCellValueFactory(new PropertyValueFactory<User, String>("Role"));

        /*tabUsId.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabUsId.setOnEditCommit(
                t -> ((User) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setUserid(t.getNewValue())
        );
        tabUsLog.setCellFactory(TextFieldTableCell.forTableColumn());
        tabUsLog.setOnEditCommit(
                t -> ((User) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setLogin(t.getNewValue())
        );
        tabUsPa.setCellFactory(TextFieldTableCell.forTableColumn());
        tabUsPa.setOnEditCommit(
                t -> ((User) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setPassword(t.getNewValue())
        );

        tabUsTh.setCellFactory(TextFieldTableCell.forTableColumn());
        tabUsTh.setOnEditCommit(
                t -> ((User) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setTheatreid(t.getNewValue())
        );
        tabUsRo.setCellFactory(TextFieldTableCell.forTableColumn());
        tabUsRo.setOnEditCommit(
                t -> ((User) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setRole(t.getNewValue())
        );*/
        tableUs.setItems(listUsers);
    }

    private void SetActor() throws SQLException {

        String SQL = "SELECT ACTORID, FIO, AGE, EXPERIENCE, CONTRACTCOST, RANG, GENRE, ADRESSID, USERID, DESCRIPTION FROM ACTOR";
        ResultSet rs = st.executeQuery(SQL);

        while (rs.next()){
            listActors.add(new Actor(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(9),rs.getString(10)));
        }

        tableAc.setEditable(true);
        tabAcId.setCellValueFactory(new PropertyValueFactory<Actor, Integer>("Actorid"));
        tabAcFio.setCellValueFactory(new PropertyValueFactory<Actor, String>("FIO"));
        tabAcY.setCellValueFactory(new PropertyValueFactory<Actor, Integer>("Age"));
        tabAcEx.setCellValueFactory(new PropertyValueFactory<Actor, Integer>("Exper"));
        tabAcCo.setCellValueFactory(new PropertyValueFactory<Actor, Integer>("Contractcost"));
        tabAcRa.setCellValueFactory(new PropertyValueFactory<Actor, String>("Rang"));
        tabAcGe.setCellValueFactory(new PropertyValueFactory<Actor, String>("Genre"));
        tabAcAd.setCellValueFactory(new PropertyValueFactory<Actor, String>("Adress"));
        tabAcUs.setCellValueFactory(new PropertyValueFactory<Actor, Integer>("Userid"));
/*
        tabAcId.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabAcId.setOnEditCommit(
                t -> ((Actor) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setActorid(t.getNewValue())
        );
        tabAcFio.setCellFactory(TextFieldTableCell.forTableColumn());
        tabAcFio.setOnEditCommit(
                t -> ((Actor) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setFIO(t.getNewValue())
        );
        tabAcY.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabAcY.setOnEditCommit(
                t -> ((Actor) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setAge(t.getNewValue())
        );
        tabAcEx.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabAcEx.setOnEditCommit(
                t -> ((Actor) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setExper(t.getNewValue())
        );
        tabAcCo.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabAcCo.setOnEditCommit(
                t -> ((Actor) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setContractcost(t.getNewValue())
        );
        tabAcRa.setCellFactory(TextFieldTableCell.forTableColumn());
        tabAcRa.setOnEditCommit(
                t -> ((Actor) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setRang(t.getNewValue())
        );
        tabAcGe.setCellFactory(TextFieldTableCell.forTableColumn());
        tabAcGe.setOnEditCommit(
                t -> ((Actor) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setGenre(t.getNewValue())
        );
        tabAcAd.setCellFactory(TextFieldTableCell.forTableColumn());
        tabAcAd.setOnEditCommit(
                t -> ((Actor) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setAdress(t.getNewValue())
        );
        tabAcUs.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabAcUs.setOnEditCommit(
                t -> ((Actor) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setUserid(t.getNewValue())
        );*/
        tableAc.setItems(listActors);
    }

    private void SetPerfomance() {
        table.setEditable(true);
        // устанавливаем тип и значение которое должно хранится в колонке
        tabId.setCellValueFactory(new PropertyValueFactory<Perfomance, Integer>("id"));
        tabname.setCellValueFactory(new PropertyValueFactory<Perfomance, String>("name"));
        tabY.setCellValueFactory(new PropertyValueFactory<Perfomance, String>("year"));
        tabBu.setCellValueFactory(new PropertyValueFactory<Perfomance, Integer>("budget"));
        tabBe.setCellValueFactory(new PropertyValueFactory<Perfomance, Date>("beginTime"));
        tabEn.setCellValueFactory(new PropertyValueFactory<Perfomance, Date>("endTime"));
        tabG.setCellValueFactory(new PropertyValueFactory<Perfomance, String>("genre"));
        tabI.setCellValueFactory(new PropertyValueFactory<Perfomance, Integer>("income"));
/*
        tabname.setCellFactory(TextFieldTableCell.forTableColumn());
        tabname.setOnEditCommit(
                t -> ((Perfomance) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
        );
        tabY.setCellFactory(TextFieldTableCell.forTableColumn());
        tabY.setOnEditCommit(
                t -> ((Perfomance) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setYear(t.getNewValue())
        );
        tabBu.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){

            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabBu.setOnEditCommit(
                t -> ((Perfomance) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setBudget(t.getNewValue())
        );
        tabBe.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
            @Override
            public String toString(Date object) {
                return object.toString();
            }

            @Override
            public Date fromString(String string) {
                return Date.valueOf(string);
            }
        }));
        tabBe.setOnEditCommit(
                t -> ((Perfomance) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setBeginTime(t.getNewValue())
        );
        tabEn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
            @Override
            public String toString(Date object) {
                return object.toString();
            }

            @Override
            public Date fromString(String string) {
                return Date.valueOf(string);
            }
        }));
        tabEn.setOnEditCommit(
                t -> ((Perfomance) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setEndTime(t.getNewValue())
        );
        tabG.setCellFactory(TextFieldTableCell.forTableColumn());
        tabG.setOnEditCommit(
                t -> ((Perfomance) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setGenre(t.getNewValue())
        );
        tabI.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){

            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        tabI.setOnEditCommit(
                t -> ((Perfomance) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setIncome(t.getNewValue())
        );
*/
        // заполняем таблицу данными
        table.setItems(data2);
    }

    private void LoaderSetStage(String view){
        /*loader = new FXMLLoader(getClass().getResource(view));
        scene = new Scene(new Group());
        try {
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.setScene(scene);
        stage.show();*/

    }

    public void The(String Type, String Name, String Addres, String Telephone){
        try {
            String SQ = "SELECT TYPEID FROM TYPE WHERE TYPE.TYPE LIKE ('" + Type + "')";
            ResultSet rs=st.executeQuery(SQ);
            if (!rs.next()) {
                System.out.println("dddddd");
            } else {
                SQ = "INSERT INTO THEATRE (NAME, ADRESS, TELEPHONE, TYPEID) " +
                        "VALUES ('"+ Name +"','"+ Addres +"',"+ Telephone +",'"+ rs.getString("TYPEID") +"')";
                st.executeUpdate(SQ);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Per(String Name, int Year, int Budget, String BT, String ET, String Genre, int Income){
        try {
            String SQ = "INSERT INTO INCOME(INCOME) VALUES ("+ Income +")";
            st.executeUpdate(SQ);
            SQ = "SELECT INCOMEID FROM INCOME WHERE INCOMEID =(SELECT MAX(INCOMEID) FROM INCOME)";
            ResultSet rd=st.executeQuery(SQ);
            int in = 0;
            while (rd.next()){
                in=rd.getInt("INCOMEID");
            }
            SQ = "INSERT INTO PERFOMANCE (NAME,YEAR,BUDGET,BEGINTIME,ENDTIME,GENRE,INCOMEID) " +
                    "VALUES ('" + Name + "'," + Year + "," + Budget + ",TO_DATE('" + BT + "', 'yyyy/mm/dd'),TO_DATE('" + ET + "', 'yyyy/mm/dd'),'" + Genre + "',"+ in +")";
            st.executeUpdate(SQ);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Act(String Name, int Age, int Experience, int Contract, String Rang, String Genre, String Adress, String User){
        try {
            String SQ = "SELECT USERID FROM USERS WHERE LOGIN LIKE ('" + User + "')";
            ResultSet rs=st.executeQuery(SQ);
            if (!rs.next()) {
                System.out.println("dddddd");
            } else {
                int n = rs.getInt("USERID");
                SQ = "INSERT INTO ACTOR (FIO,AGE,EXPERIENCE,CONTRACTCOST,RANG,GENRE,ADRESSID,USERID,DESCRIPTION) " +
                        "VALUES ('"+ Name +"',"+ Age +","+ Experience +","+ Contract +",'"+ Rang +"','"+ Genre +"','"+ Adress +"',"+ n +",null)";
                st.executeUpdate(SQ);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Emp(String Actor, String Performance, String Role, int Contract){
        try {
            String SQ = "SELECT PERFOMANCEID FROM PERFOMANCE WHERE PERFOMANCE.NAME LIKE ('" + Performance + "')";
            ResultSet rs=st.executeQuery(SQ);
            rs.next();
            int n = rs.getInt("PERFOMANCEID");
            SQ = "SELECT ACTORID FROM ACTOR WHERE ACTOR.FIO LIKE ('" + Actor + "')";
            rs=st.executeQuery(SQ);
            if (!rs.next()) {
                System.out.println("dddddd");
            } else {
                SQ = "INSERT INTO EMPLOYMENT (ACTORID, PERFOMANCEID, ROLE, CONTRACT) " +
                        "VALUES ("+ rs.getInt("ACTORID") +","+ n +",'"+ Role +"',"+ Contract +")";
                st.executeUpdate(SQ);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Pos(String FIO, String Position){
        try {
            String SQ = "SELECT POSITIONID FROM POSITION WHERE POSITION.NAME LIKE ('" + Position + "')";
            ResultSet rs=st.executeQuery(SQ);
            if (!rs.next()) {
                System.out.println("dddddd");
            } else {
                SQ = "INSERT INTO CREATOR (FIO, POSITIONID) " +
                        "VALUES ('"+ FIO +"',"+ rs.getInt("POSITIONID") +")";
                st.executeUpdate(SQ);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setDB() throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        String url = "jdbc:oracle:thin:@//localhost:1521/XE";
        String login = "System";
        String pass = "Admin";
        c = DriverManager.getConnection(url,login,pass);
        st = c.createStatement();
    }

    public void Register(String login, String password, String theatre){
        try {
            if (theatre!="null") {
                String SQL = "SELECT THEATREID FROM THEATRE WHERE NAME LIKE ('" + theatre + "')";
                ResultSet rs = st.executeQuery(SQL);
                rs.next();
                SQL = "INSERT INTO USERS (LOGIN,PASSWORD,THEATREID,ROLE) VALUES ('" + login + "','" + password + "','" + rs.getString("THEATREID") + "',null)";
                st.executeUpdate(SQL);
            }else {
                String SQL = "INSERT INTO USERS (LOGIN,PASSWORD,THEATREID,ROLE) VALUES ('" + login + "','" + password + "',null,null)";
                st.executeUpdate(SQL);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Registered!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Sho(String Theatre, String Performance, String Time, int FreeSeats) {
        try {
            String SQ = "SELECT PERFOMANCEID FROM PERFOMANCE WHERE PERFOMANCE.NAME LIKE ('" + Performance + "')";
            ResultSet rs=st.executeQuery(SQ);
            rs.next();
            int n = rs.getInt("PERFOMANCEID");
            SQ = "SELECT THEATREID FROM THEATRE WHERE THEATRE.NAME LIKE ('" + Theatre + "')";
            rs=st.executeQuery(SQ);
            if (!rs.next()) {
                System.out.println("dddddd");
            } else {
                SQ = "INSERT INTO SHOW (THEATREID, PERFOMANCEID, TIMET, FREESEATS) " +
                        "VALUES ("+ rs.getInt("THEATREID") +","+ n +",'"+ Time +"',"+ FreeSeats +")";
                st.executeUpdate(SQ);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class Employment{
        private String Actorid;
        private String Perfomanceid;
        private String Role;
        private int Contract;

        private Employment(String Actorid, String Perfomanceid, String Role, int Contract){
            this.Actorid=Actorid;
            this.Perfomanceid=Perfomanceid;
            this.Role=Role;
            this.Contract=Contract;
        }

        public String getActorid() {
            return Actorid;
        }

        public void setActorid(String actorid) {
            Actorid = actorid;
        }

        public String getPerfomanceid() {
            return Perfomanceid;
        }

        public void setPerfomanceid(String perfomanceid) {
            Perfomanceid = perfomanceid;
        }

        public String getRole() {
            return Role;
        }

        public void setRole(String role) {
            Role = role;
        }

        public int getContract() {
            return Contract;
        }

        public void setContract(int contract) {
            Contract = contract;
        }
    }

    public class Show{
        private String Theatreid;
        private String Perfomanceid;
        private String Time;
        private int Showid;
        private int Freeseats;

        private Show(String Theatreid, String Perfomanceid, String Time, int Showid, int Freeseats){
            this.Theatreid=Theatreid;
            this.Perfomanceid=Perfomanceid;
            this.Time=Time;
            this.Showid=Showid;
            this.Freeseats=Freeseats;
        }

        public String getTheatreid() {
            return Theatreid;
        }

        public void setTheatreid(String theatreid) {
            Theatreid = theatreid;
        }

        public String getPerfomanceid() {
            return Perfomanceid;
        }

        public void setPerfomanceid(String perfomanceid) {
            Perfomanceid = perfomanceid;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public int getShowid() {
            return Showid;
        }

        public void setShowid(int showid) {
            Showid = showid;
        }

        public int getFreeseats() {
            return Freeseats;
        }

        public void setFreeseats(int freeseats) {
            Freeseats = freeseats;
        }
    }

    public class Creator{
        private int Creatorid;
        private String Fio;
        private String Positionid;

        public Creator(int Creatorid, String Fio, String Positionid){
            this.Creatorid=Creatorid;
            this.Fio=Fio;
            this.Positionid=Positionid;
        }

        public int getCreatorid() {
            return Creatorid;
        }

        public void setCreatorid(int creatorid) {
            Creatorid = creatorid;
        }

        public String getFio() {
            return Fio;
        }

        public void setFio(String fio) {
            Fio = fio;
        }

        public String getPositionid() {
            return Positionid;
        }

        public void setPositionid(String positionid) {
            Positionid = positionid;
        }
    }

    private class CreatorList{
        private String Creatorid;
        private String Perfomanseid;
        private String Position;

        private CreatorList(String Creatorid, String Perfomanseid, String Position){
            this.Creatorid=Creatorid;
            this.Perfomanseid=Perfomanseid;
            this.Position=Position;
        }

        public String getCreatorid() {
            return Creatorid;
        }

        public void setCreatorid(String creatorid) {
            Creatorid = creatorid;
        }

        public String getPerfomanseid() {
            return Perfomanseid;
        }

        public void setPerfomanseid(String perfomanseid) {
            Perfomanseid = perfomanseid;
        }

        public String getPosition() {
            return Position;
        }

        public void setPosition(String position) {
            Position = position;
        }
    }
}
