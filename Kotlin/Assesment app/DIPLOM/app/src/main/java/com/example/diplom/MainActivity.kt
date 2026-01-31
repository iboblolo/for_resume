package com.example.diplom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.diplom.db.AppDatabase
import com.example.diplom.db.CompletedTest
import com.example.diplom.db.Question
import com.example.diplom.db.Test
import com.example.diplom.db.User


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                Navigation()
        }
    }
    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController,
            startDestination = Screen.LoginScreen.route) {
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navController = navController)
            }
            composable(
                route = Screen.AppScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                )
            ) { entry ->
                AppScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.RegisterScreen.route)
            {
                RegisterScreen(navController)
            }
            composable(route = Screen.NewTestScreen.route + "/{name}"
            ,arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                NewTestScreen(name = entry.arguments?.getString("name"),navController)
            }
            composable(route = Screen.CreatingNewTestScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                CreatingNewTestScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.TestToRecreateScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                TestToRecreateScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.RecreatingTestScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                RecreatingTestScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.CreateNewUserScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                CreateNewUserScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.UpdateUserInformationScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                UpdateUserInformationScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.TestToCompleteScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                TestToCompleteScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.TestingScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                TestingScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.ResultsScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                ResultsScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.DeleteTestScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                DeleteTestScreen(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.UserForUserResults.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                UserForUserResults(name = entry.arguments?.getString("name"), navController)
            }
            composable(route = Screen.UserResults.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Some Default"
                        nullable = true
                    }
                ))
            {entry ->
                UserResults(name = entry.arguments?.getString("name"), navController)
            }
        }
    }

    private @Composable
    fun UserResults(name: String?, navController: NavController)
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()
            val dao = db.dbDao()

            val log = name!!.split("<>")[1]

            Text(
                text = "Результаты " + dao.loadUserByLogin(log)!!.firstName + " " +
                dao.loadUserByLogin(log)!!.lastName,
                fontSize = 40.sp,
                modifier = Modifier.padding(5.dp)
            )


            val testList = remember {dao.getUserCompletedTests(
                dao.loadUserByLogin(log)!!.userId).toMutableStateList()}
            LazyColumn (modifier = Modifier.padding(5.dp)){
                items(testList)
                {test ->
                    if(dao.getAllTests().contains(dao.getTest(test.completedTestId)))
                    {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = dao.getTest(test.completedTestId).testName
                                    + "\t\t" + test.result,
                            fontSize = 30.sp,
                            color = colorResource(R.color.mycolor),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .bottomBorder(1.dp, Color.Black)
                        )
                    }
                }

            }
        }
    }

    private @Composable
    fun UserForUserResults(name: String?, navController: NavController)
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(
                text = "Выберите ученика результаты которого хотите посмотреть",
                fontSize = 40.sp,
                modifier = Modifier.padding(5.dp)
            )
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()
            val dao = db.dbDao()

            val userList = remember {dao.getAllUsers().toMutableStateList()}
            LazyColumn (modifier = Modifier.padding(5.dp)){
                items(userList)
                {u ->
                    if(!u.isTeacher && u.firstName != "" && u.lastName != "")
                    {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = u.firstName.toString() + " " + u.lastName,
                            fontSize = 30.sp,
                            color = colorResource(R.color.mycolor),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .bottomBorder(1.dp, Color.Black)
                                .clickable {
                                    val inf = name.toString() + "<>" + u.login.toString()
                                    navController.navigate(
                                        Screen.UserResults.withArgs(inf)
                                    )
                                }
                        )
                    }
                }
            }
        }

    }

    private @Composable
    fun DeleteTestScreen(name: String?, navController: NavController)
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(
                text = "Выберите тест который хотите удалить",
                fontSize = 40.sp,
                modifier = Modifier.padding(5.dp)
            )
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()
            val dao = db.dbDao()

            var deleted = remember { mutableStateOf(false) }

            var testList = remember {dao.getAllTests().toMutableStateList()}
            if(deleted.value)
            {
                Alert2(name = "Тест был удалён", showDialog = deleted.value
                    , onDismiss = {deleted.value = false})
            }
            if(!deleted.value)
            {
                LazyColumn (modifier = Modifier.padding(5.dp)){
                    itemsIndexed(testList)
                    {i, test ->
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = test.testName,
                            fontSize = 30.sp,
                            color = colorResource(R.color.mycolor),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .bottomBorder(1.dp, Color.Black)
                                .clickable {
                                    val inf = name.toString() + "<>" + test.testId.toString()
                                    dao.deleteTest(test)
                                    testList.clear()
                                    testList = dao
                                        .getAllTests()
                                        .toMutableStateList()
                                    deleted.value = true
                                }
                        )
                    }

                }
            }
           
        }

    }

    private @Composable
    fun ResultsScreen(name: String?, navController: NavController) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(
                text = "Ваши результаты",
                fontSize = 40.sp,
                modifier = Modifier.padding(5.dp)
            )
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()
            val dao = db.dbDao()

            val log = name!!.split(":;:")[1]

            val testList = remember {dao.getUserCompletedTests(
                dao.loadUserByLogin(log)!!.userId).toMutableStateList()}
            LazyColumn (modifier = Modifier.padding(5.dp)){
                items(testList)
                {test ->
                    if(dao.getAllTests().contains(dao.getTest(test.completedTestId)))
                    {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(

                            text = dao.getTest(test.completedTestId).testName
                                    + "\t\t" + test.result,
                            fontSize = 30.sp,
                            color = colorResource(R.color.mycolor),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .bottomBorder(1.dp, Color.Black)
                        )
                    }
                }

            }
        }
    }

    private @Composable
    fun TestingScreen(name: String?, navController: NavController)
    {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()
            val dao = db.dbDao()

            val tId = name!!.split("<>")[1]
            val stTest = dao.getTest(tId.toInt())

            val questionsIds = stTest.questions.split("<;>").map{ it.toInt() }

            val questionList = dao.loadQuestionsByIds(questionsIds)

            var newQuestionList = remember {questionList.toMutableStateList()}

            var ft = remember { mutableStateOf(true) }

            if(ft.value)
            {
                newQuestionList.forEachIndexed()
                {index, question ->
                    newQuestionList[index].trueAnswer = ""
                }
                ft.value = false
            }

            val nowQuestion = remember { mutableIntStateOf(0) }
            val testName = remember { mutableStateOf(stTest.testName) }
            val recompose = remember { mutableStateOf(true) }
            val questionIsSaved = remember { mutableStateOf(true) }
            val newQuestionJustAdded = remember { mutableStateOf(false) }

            Column(modifier = Modifier
                .fillMaxSize())
            {
                Row( modifier = Modifier.verticalScroll(ScrollState(0)))
                {
                    if(!newQuestionJustAdded.value && questionIsSaved.value)
                    {
                        newQuestionList.forEachIndexed{ index, question ->
                            Button(onClick = {
                                nowQuestion.intValue = index
                                recompose.value = false
                                questionIsSaved.value = false
                                newQuestionList[nowQuestion.intValue].questionId = index
                            },
                                Modifier
                                    .height(60.dp)
                                    .padding(5.dp)) {
                                Text(text = (index + 1).toString(),
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp)
                            }
                        }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(value = testName.value,
                        label = { Text(text = "Название теста")},
                        placeholder = { Text(text = "Введите название теста")},
                        onValueChange = {
                            testName.value = it
                        },
                        enabled = false,
                        modifier = Modifier
                            .width(250.dp)
                            .padding(30.dp)
                    )
                    Button(
                        onClick = {
                            val log = name.split("<>")[0].split(":;:")[1]
                            val u = dao.loadUserByLogin(log)
                            var corrAns = 0
                            questionList.forEachIndexed()
                            { index, question ->

                                if (questionList[index].trueAnswer
                                    == newQuestionList[index].trueAnswer) {
                                    corrAns += 1
                                }
                            }

                            val cTest = CompletedTest(
                                stTest.testId, u!!.userId,
                                corrAns.toString() + " / " + questionList.count())
                            dao.insertCompletedTest(cTest)
                            val n = name.split("<>")[0]
                            navController.navigate(Screen.AppScreen.withArgs(n)) },
                    ) {
                        Text(text = "Завершить тест",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp)
                    }
                }
                if(recompose.value)
                {
                    Box(modifier = Modifier.fillMaxSize())
                }
                if (newQuestionList.isNotEmpty() &&
                    newQuestionList[nowQuestion.intValue].answerType == 1 &&
                    !recompose.value)
                {
                    var questionText by remember { mutableStateOf(newQuestionList[nowQuestion.intValue].questionText) }
                    var answerText by remember { mutableStateOf(newQuestionList[nowQuestion.intValue].trueAnswer) }

                    Text(text = "Вопрос: " + questionText,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)

                    TextField(value = answerText,
                        label = { Text(text = "Ответ")},
                        placeholder = { Text(text = "Ответ на ваш вопрос")},
                        onValueChange = {
                            answerText = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp))
                    Button(onClick = {
                        newQuestionList[nowQuestion.intValue].questionText = questionText
                        newQuestionList[nowQuestion.intValue].trueAnswer = answerText
                        recompose.value = true
                        questionIsSaved.value = true
                    },
                        Modifier
                            .height(60.dp)
                            .padding(5.dp)) {
                        Text(text = "Сохранить ответ",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp)
                    }
                }
                if (newQuestionList.isNotEmpty() &&
                    newQuestionList[nowQuestion.intValue].answerType == 2 &&
                    !recompose.value)
                {
                    var questionText by remember {
                        mutableStateOf(newQuestionList[nowQuestion.intValue].questionText) }
                    val ansTexts = remember { newQuestionList[nowQuestion.intValue]
                        .allAnswers.toMutableStateList()}
                    var selectedOption = remember { mutableStateListOf<Boolean>() }
                    if(ansTexts.isNotEmpty())
                    {
                        newQuestionList[nowQuestion.intValue].trueAnswer.forEach()
                        {t->
                            if(t == '0')
                            {
                                selectedOption.add(false)
                            }
                            else{
                                selectedOption.add(true)
                            }
                        }
                    }
                    if(selectedOption.isEmpty())
                    {
                        selectedOption = remember { BooleanArray(ansTexts.count()).toList().toMutableStateList() }
                    }

                    Text(text = questionText,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)

                    Column (
                        Modifier
                            .selectableGroup()
                            .verticalScroll(ScrollState(0))){
                        ansTexts.forEachIndexed()
                        {index, item ->
                            Row (verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.horizontalScroll(ScrollState(0))
                            ){
                                RadioButton(selected = ( selectedOption[index]),
                                    onClick = {
                                        if(selectedOption.contains(true))
                                        {
                                            selectedOption[ selectedOption.indexOf(true)] = false
                                            selectedOption[index] = true
                                        }
                                        else {selectedOption[index] = true}
                                    })
                                Text(
                                    text = item,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                        Button(onClick = {
                            newQuestionList[nowQuestion.intValue].questionText = questionText
                            newQuestionList[nowQuestion.intValue].allAnswers = ansTexts
                            var t = ""
                            selectedOption.forEach()
                            {b ->
                                if(!b)
                                {
                                    t+= "0"
                                }
                                else
                                {
                                    t+= "1"
                                }
                            }
                            newQuestionList[nowQuestion.intValue].trueAnswer = t
                            recompose.value = true
                            questionIsSaved.value = true
                        },
                            Modifier
                                .height(60.dp)
                                .padding(5.dp)) {
                            Text(text = "Сохранить ответ",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp)
                        }
                    }

                }
                if (newQuestionList.isNotEmpty() &&
                    newQuestionList[nowQuestion.intValue].answerType == 3 &&
                    !recompose.value)
                {
                    var questionText by remember {
                        mutableStateOf(newQuestionList[nowQuestion.intValue].questionText) }
                    val ansTexts = remember { newQuestionList[nowQuestion.intValue].allAnswers.toMutableStateList()}
                    var selectedOption = remember { mutableStateListOf<Boolean>() }
                    if(ansTexts.isNotEmpty())
                    {
                        newQuestionList[nowQuestion.intValue].trueAnswer.forEach()
                        {t->
                            if(t == '0')
                            {
                                selectedOption.add(false)
                            }
                            else{
                                selectedOption.add(true)
                            }
                        }
                    }
                    if(selectedOption.isEmpty())
                    {
                        selectedOption = remember { BooleanArray(ansTexts.count()).toList().toMutableStateList() }
                    }
                    Text(text = questionText,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)

                    Column (
                        Modifier
                            .selectableGroup()
                            .verticalScroll(ScrollState(0))){
                        ansTexts.forEachIndexed()
                        {index, item ->
                            Row (verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.horizontalScroll(ScrollState(0))
                            ){
                                Checkbox(checked = selectedOption[index],
                                    onCheckedChange = {
                                        if(selectedOption.isNotEmpty())
                                        {
                                            if(selectedOption[index])
                                            {
                                                selectedOption[index] = false
                                            }
                                            else{
                                                selectedOption[index] = true
                                            }
                                        }
                                    })
                                Text(
                                    text = item,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        Button(onClick = {
                            newQuestionList[nowQuestion.intValue].questionText = questionText
                            newQuestionList[nowQuestion.intValue].allAnswers = ansTexts
                            var t = ""
                            selectedOption.forEach()
                            {b ->
                                if(!b)
                                {
                                    t+= "0"
                                }
                                else
                                {
                                    t+= "1"
                                }
                            }
                            newQuestionList[nowQuestion.intValue].trueAnswer = t
                            recompose.value = true
                            questionIsSaved.value = true
                        },
                            Modifier
                                .height(60.dp)
                                .padding(5.dp)) {
                            Text(text = "Сохранить ответ",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp)
                        }
                    }

                }
            }

        }

    private @Composable
    fun TestToCompleteScreen(name: String?, navController: NavController)
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(
                text = "Выберите тест который хотите пройти",
                fontSize = 40.sp,
                modifier = Modifier.padding(5.dp)
            )
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()
            val dao = db.dbDao()

            val testList = remember {dao.getAllTests().toMutableStateList()}
            LazyColumn (modifier = Modifier.padding(5.dp)){
                items(testList)
                {test ->
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = test.testName,
                        fontSize = 30.sp,
                        color = colorResource(R.color.mycolor),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .bottomBorder(1.dp, Color.Black)
                            .clickable {
                                val inf = name.toString() + "<>" + test.testId.toString()
                                navController.navigate(
                                    Screen.TestingScreen.withArgs(inf)
                                )
                            }
                    )
                }

            }
        }

    }

    private @Composable
    fun UpdateUserInformationScreen(name: String?, navController: NavController)
    {
        var n = name!!.split(":;:")[0]
        var log = name.split(":;:")[1]

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        val dao = db.dbDao()

        var stUser = dao.loadUserByLogin(log)

            var loginFieldText by remember { mutableStateOf(log) }
            var passwordFieldText by remember { mutableStateOf(stUser!!.password) }
            var nameFieldText by remember { mutableStateOf(n.split(" ")[0]) }
            var surnameFieldText by remember { mutableStateOf(n.split(" ")[1]) }

            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                TextField(value = loginFieldText,
                    label = { Text(text = "Логин")},
                    placeholder = { Text(text = "Введите ваш логин")},
                    onValueChange = {
                        loginFieldText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )
                TextField(value = passwordFieldText,
                    label = { Text(text = "Пароль")},
                    placeholder = { Text(text = "Введите ваш пароль")},
                    onValueChange = {
                        passwordFieldText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )
                TextField(value = nameFieldText,
                    label = { Text(text = "Имя")},
                    placeholder = { Text(text = "Введите ваше имя")},
                    onValueChange = {
                        nameFieldText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )
                TextField(value = surnameFieldText,
                    label = { Text(text = "Фамилия")},
                    placeholder = { Text(text = "Введите вашу фамилию")},
                    onValueChange = {
                        surnameFieldText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )

                Button(modifier = Modifier.padding(5.dp),
                    onClick = {
                        stUser!!.login = loginFieldText
                        stUser.password = passwordFieldText
                        stUser.firstName = nameFieldText
                        stUser.lastName = surnameFieldText
                        dao.updateUser(stUser)

                        val inf = stUser.firstName + " " + stUser.lastName + ":;:" + stUser.login
                        navController.navigate(Screen.AppScreen.withArgs(inf.toString()))
                    }) {
                    Text(text = "Обновить аккаунт")
                }
            }
        }

    private @Composable
    fun CreateNewUserScreen(name: String?, navController: NavController)
    {

            var loginFieldText by remember { mutableStateOf("") }
            var passwordFieldText by remember { mutableStateOf("") }
            var nameFieldText by remember { mutableStateOf("") }
            var surnameFieldText by remember { mutableStateOf("") }
            var isTeacherBox by remember { mutableStateOf(false)}

            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                TextField(value = loginFieldText,
                    label = { Text(text = "Логин")},
                    placeholder = { Text(text = "Введите ваш логин")},
                    onValueChange = {
                        loginFieldText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )
                TextField(value = passwordFieldText,
                    label = { Text(text = "Пароль")},
                    placeholder = { Text(text = "Введите ваш пароль")},
                    onValueChange = {
                        passwordFieldText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )
                TextField(value = nameFieldText,
                    label = { Text(text = "Имя")},
                    placeholder = { Text(text = "Введите ваше имя")},
                    onValueChange = {
                        nameFieldText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )
                TextField(value = surnameFieldText,
                    label = { Text(text = "Фамилия")},
                    placeholder = { Text(text = "Введите вашу фамилию")},
                    onValueChange = {
                        surnameFieldText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )
                Row (verticalAlignment = Alignment.CenterVertically){
                    Checkbox(checked = isTeacherBox, onCheckedChange = {
                        if(isTeacherBox)
                        {
                            isTeacherBox = false
                        }
                        else isTeacherBox = true
                    })
                    Text(text = "Учитель")
                }

                Button(modifier = Modifier.padding(5.dp),
                    onClick = {
                        val db = Room.databaseBuilder(
                            applicationContext,
                            AppDatabase::class.java, "database-name"
                        ).allowMainThreadQueries().build()
                        val dao = db.dbDao()
                        dao.insertUser(User(login = loginFieldText, password = passwordFieldText,
                            firstName = nameFieldText, lastName = surnameFieldText, isTeacher = isTeacherBox)
                        )
                        navController.navigate(Screen.AppScreen.withArgs(name.toString()))
                    }) {
                    Text(text = "Добавить аккаунт")
                }
            }
        }

    @Composable
    fun RecreatingTestScreen(name: String?, navController: NavController)
    {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        val dao = db.dbDao()

        val tId = name!!.split("<>")[1]
        val stTest = dao.getTest(tId.toInt())

        val questionsIds = stTest.questions.split("<;>").map{ it.toInt() }

        val questionList = remember { dao.loadQuestionsByIds(questionsIds).toMutableStateList() }
        val nowQuestion = remember { mutableIntStateOf(0) }
        val testName = remember { mutableStateOf(stTest.testName) }
        val recompose = remember { mutableStateOf(true) }
        val questionIsSaved = remember { mutableStateOf(true) }
        val newQuestionJustAdded = remember { mutableStateOf(false) }

        val showDialog = remember { mutableStateOf(false) }
        val showDialog2 = remember { mutableStateOf(false) }
        val showDialog3 = remember { mutableStateOf(false) }

        Column(modifier = Modifier
            .fillMaxSize())
        {
            Row( modifier = Modifier.verticalScroll(ScrollState(0)))
            {
                if(!newQuestionJustAdded.value && questionIsSaved.value)
                {
                    questionList.forEachIndexed{ index, question ->
                        Button(onClick = {
                            nowQuestion.intValue = index
                            recompose.value = false
                            questionIsSaved.value = false
                            questionList[nowQuestion.intValue].questionId = index
                        },
                            Modifier
                                .height(60.dp)
                                .padding(5.dp)) {
                            Text(text = (index + 1).toString(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp)
                        }
                    }
                }
            }
            if(questionIsSaved.value)
            {
                Button(onClick = {
                    newQuestionJustAdded.value = true
                    if(questionList.isNotEmpty()){
                        nowQuestion.intValue =
                            questionList[questionList.count() - 1].questionId
                    }
                    if(nowQuestion.value >= questionList.count())
                    {
                        nowQuestion.value = questionList.count() - 1
                    }
                    questionIsSaved.value = false
                },
                    Modifier
                        .height(60.dp)
                        .padding(5.dp)) {
                    Text(text = "Добавить вопрос",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(value = testName.value,
                    label = { Text(text = "Название теста")},
                    placeholder = { Text(text = "Введите название теста")},
                    onValueChange = {
                        testName.value = it
                    },
                    modifier = Modifier
                        .width(250.dp)
                        .padding(30.dp))
                Button(onClick = {
                    if(questionList.isNotEmpty())
                    {
                        if(testName.value != "")
                        {
                                var qList = ""
                                questionList.toMutableStateList().forEachIndexed()
                                {index, item ->
                                    questionList.toMutableStateList()[index].questionId = 0
                                    var ans = dao.insertQuestion(questionList.toMutableStateList()[index])
                                    qList += ans.toString() + "<;>"
                                }
                                qList = qList.dropLast(3)
                                stTest.questions = qList
                                stTest.testName = testName.value
                                dao.updateTest(stTest)

                                val n = name.split("<>")[0]

                                //dao.updateTestWithQuestions(QuestionsByTest(stTest,
                                //    questionList))

                                navController.navigate(Screen.AppScreen.withArgs(n))
                        }
                        else
                        {
                            showDialog2.value = true
                        }
                    }
                    else
                    {
                        showDialog.value = true
                    }

                },
                ) {
                    Text(text = "Изменить тест",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp)
                }
            }
            if(showDialog.value)
            {
                Alert(name = "Добавьте вопросы в тест", showDialog.value,
                    onDismiss = {showDialog.value = false})
            }
            if(showDialog2.value)
            {
                Alert(name = "Назовите как-нибудь тест", showDialog2.value,
                    onDismiss = {showDialog2.value = false})
            }
            if(showDialog3.value)
            {
                Alert(name = "Такой тест уже существует", showDialog3.value,
                    onDismiss = {showDialog3.value = false})
            }
            if(questionList.isNotEmpty() && !recompose.value)
            {
                Button(onClick = {
                    questionList.removeAt(nowQuestion.value)
                    nowQuestion.value = questionList.count() - 1
                    questionIsSaved.value = true
                    recompose.value = true
                },
                    Modifier
                        .height(60.dp)
                        .padding(5.dp)) {
                    Text(text = "Удалить этот вопрос",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)
                }
            }
            if(newQuestionJustAdded.value)
            {
                DropdownMenu(expanded = true, onDismissRequest = {}) {
                    Text("Вопрос с ответом строкой",
                        fontSize=18.sp, modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                onClick = {
                                    questionList.add(
                                        Question(
                                            questionList.count(), "",
                                            mutableListOf(), "", 1
                                        )
                                    )
                                    recompose.value = false
                                    newQuestionJustAdded.value = false
                                    if (questionList.isNotEmpty()) {
                                        nowQuestion.intValue =
                                            questionList[questionList.count() - 1].questionId
                                    }

                                }))
                    Text("Вопрос с вариантом ответа",
                        fontSize=18.sp, modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                onClick = {
                                    questionList.add(
                                        Question(
                                            questionList.count(), "",
                                            mutableListOf(), "", 2
                                        )
                                    )
                                    recompose.value = false
                                    newQuestionJustAdded.value = false
                                    if (questionList.isNotEmpty()) {
                                        nowQuestion.intValue =
                                            questionList[questionList.count() - 1].questionId
                                    }

                                }))
                    Text("Вопрос с несколькими вариантами ответа",
                        fontSize=18.sp, modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                onClick = {
                                    questionList.add(
                                        Question(
                                            questionList.count(), "",
                                            mutableListOf(), "", 3
                                        )
                                    )
                                    recompose.value = false
                                    newQuestionJustAdded.value = false
                                    if (questionList.isNotEmpty()) {
                                        nowQuestion.intValue =
                                            questionList[questionList.count() - 1].questionId
                                    }

                                }))
                }
            }
            if(recompose.value)
            {
                Box(modifier = Modifier.fillMaxSize())
            }
            if (questionList.isNotEmpty() &&
                questionList[nowQuestion.intValue].answerType == 1 &&
                !recompose.value)
            {
                var questionText by remember { mutableStateOf(questionList[nowQuestion.intValue].questionText) }
                var answerText by remember { mutableStateOf(questionList[nowQuestion.intValue].trueAnswer) }

                TextField(value = questionText,
                    label = { Text(text = "Ваш вопрос")},
                    placeholder = { Text(text = "Введите ваш вопрос")},
                    onValueChange = {
                        questionText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp))

                TextField(value = answerText,
                    label = { Text(text = "Ответ")},
                    placeholder = { Text(text = "Ответ на ваш вопрос")},
                    onValueChange = {
                        answerText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp))
                Button(onClick = {
                    questionList[nowQuestion.intValue].questionText = questionText
                    questionList[nowQuestion.intValue].trueAnswer = answerText
                    recompose.value = true
                    questionIsSaved.value = true
                },
                    Modifier
                        .height(60.dp)
                        .padding(5.dp)) {
                    Text(text = "Сохранить вопрос",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)
                }
            }
            if (questionList.isNotEmpty() &&
                questionList[nowQuestion.intValue].answerType == 2 &&
                !recompose.value)
            {
                var questionText by remember {
                    mutableStateOf(questionList[nowQuestion.intValue].questionText) }
                val ansTexts = remember { questionList[nowQuestion.intValue]
                    .allAnswers.toMutableStateList()}
                val selectedOption = remember { mutableStateListOf<Boolean>() }
                if(ansTexts.isNotEmpty())
                {
                    questionList[nowQuestion.intValue].trueAnswer.forEach()
                    {t->
                        if(t == '0')
                        {
                            selectedOption.add(false)
                        }
                        else{
                            selectedOption.add(true)
                        }
                    }
                }

                TextField(value = questionText,
                    label = { Text(text = "Ваш вопрос")},
                    placeholder = { Text(text = "Введите ваш вопрос")},
                    onValueChange = {
                        questionText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp))

                Column (
                    Modifier
                        .selectableGroup()
                        .verticalScroll(ScrollState(0))){
                    ansTexts.forEachIndexed()
                    {index, item ->
                        Row (verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.horizontalScroll(ScrollState(0))
                        ){
                            RadioButton(selected = ( selectedOption[index]),
                                onClick = {
                                    if(selectedOption.contains(true))
                                    {
                                        selectedOption[ selectedOption.indexOf(true)] = false
                                        selectedOption[index] = true
                                    }
                                    else {selectedOption[index] = true}
                                })
                            Text(
                                text = item,
                                textAlign = TextAlign.Center,
                            )
                            TextField(value = item,
                                label = { Text(text = "Вариант")},
                                placeholder = { Text(text = "Вариант ответа")},
                                onValueChange = {
                                    ansTexts[index] = it
                                },
                                modifier = Modifier
                                    .width(200.dp))

                            Button(onClick = {
                                ansTexts.removeAt(index)
                                selectedOption.removeAt(index)
                            },
                                Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(5.dp)) {
                                Text(text = "-",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp)
                            }

                        }
                    }
                    Button(onClick = {
                        ansTexts.add("")
                        selectedOption.add(false)
                    },
                        Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .padding(5.dp)) {
                        Text(text = "+",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp)
                    }
                    Button(onClick = {
                        questionList[nowQuestion.intValue].questionText = questionText
                        questionList[nowQuestion.intValue].allAnswers = ansTexts
                        var t = ""
                        selectedOption.forEach()
                        {b ->
                            if(!b)
                            {
                                t+= "0"
                            }
                            else
                            {
                                t+= "1"
                            }
                        }
                        questionList[nowQuestion.intValue].trueAnswer = t
                        recompose.value = true
                        questionIsSaved.value = true
                    },
                        Modifier
                            .height(60.dp)
                            .padding(5.dp)) {
                        Text(text = "Сохранить вопрос",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp)
                    }
                }

            }
            if (questionList.isNotEmpty() &&
                questionList[nowQuestion.intValue].answerType == 3 &&
                !recompose.value)
            {
                var questionText by remember {
                    mutableStateOf(questionList[nowQuestion.intValue].questionText) }
                val ansTexts = remember { questionList[nowQuestion.intValue].allAnswers.toMutableStateList()}
                val selectedOption = remember { mutableStateListOf<Boolean>() }
                if(ansTexts.isNotEmpty())
                {
                    questionList[nowQuestion.intValue].trueAnswer.forEach()
                    {t->
                        if(t == '0')
                        {
                            selectedOption.add(false)
                        }
                        else{
                            selectedOption.add(true)
                        }
                    }
                }

                TextField(value = questionText,
                    label = { Text(text = "Ваш вопрос")},
                    placeholder = { Text(text = "Введите ваш вопрос")},
                    onValueChange = {
                        questionText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp))

                Column (
                    Modifier
                        .selectableGroup()
                        .verticalScroll(ScrollState(0))){
                    ansTexts.forEachIndexed()
                    {index, item ->
                        Row (verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.horizontalScroll(ScrollState(0))
                        ){
                            Checkbox(checked = selectedOption[index],
                                onCheckedChange = {
                                    if(selectedOption[index])
                                    {
                                        selectedOption[index] = false
                                    }
                                    else{
                                        selectedOption[index] = true
                                    }
                                })
                            Text(
                                text = item,
                                textAlign = TextAlign.Center,
                            )
                            TextField(value = item,
                                label = { Text(text = "Вариант")},
                                placeholder = { Text(text = "Вариант ответа")},
                                onValueChange = {
                                    ansTexts[index] = it
                                },
                                modifier = Modifier
                                    .width(200.dp))

                            Button(onClick = {
                                ansTexts.removeAt(index)
                                selectedOption.removeAt(index)
                            },
                                Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(5.dp)) {
                                Text(text = "-",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp)
                            }

                        }
                    }
                    Button(onClick = {
                        ansTexts.add("")
                        selectedOption.add(false)
                    },
                        Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .padding(5.dp)) {
                        Text(text = "+",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp)
                    }
                    Button(onClick = {
                        questionList[nowQuestion.intValue].questionText = questionText
                        questionList[nowQuestion.intValue].allAnswers = ansTexts
                        var t = ""
                        selectedOption.forEach()
                        {b ->
                            if(!b)
                            {
                                t+= "0"
                            }
                            else
                            {
                                t+= "1"
                            }
                        }
                        questionList[nowQuestion.intValue].trueAnswer = t
                        recompose.value = true
                        questionIsSaved.value = true
                    },
                        Modifier
                            .height(60.dp)
                            .padding(5.dp)) {
                        Text(text = "Сохранить вопрос",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp)
                    }
                }

            }
        }

    }

    @Composable
    fun TestToRecreateScreen(name: String?, navController: NavController)
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(
                text = "Выберите тест который хотите редактировать",
                fontSize = 40.sp,
                modifier = Modifier.padding(5.dp)
            )
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()
            val dao = db.dbDao()

            val testList = remember {dao.getAllTests().toMutableStateList()}
            LazyColumn (modifier = Modifier.padding(5.dp)){
                items(testList)
                {test ->
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = test.testName,
                        fontSize = 30.sp,
                        color = colorResource(R.color.mycolor),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .bottomBorder(1.dp, Color.Black)
                            .clickable {
                                val inf = name.toString() + "<>" + test.testId.toString()
                                navController.navigate(
                                    Screen.RecreatingTestScreen.withArgs(inf)
                                )
                            }
                    )
                }

            }
        }

    }

    private @Composable
    fun CreatingNewTestScreen(name: String?, navController: NavController) {

        val questionList = remember { mutableStateListOf<Question>() }
        val nowQuestion = remember { mutableIntStateOf(0) }
        val testName = remember { mutableStateOf("") }
        val recompose = remember { mutableStateOf(false) }
        val questionIsSaved = remember { mutableStateOf(true) }
        val newQuestionJustAdded = remember { mutableStateOf(false) }

        val showDialog = remember { mutableStateOf(false) }
        val showDialog2 = remember { mutableStateOf(false) }
        val showDialog3 = remember { mutableStateOf(false) }


        Column(modifier = Modifier
            .fillMaxSize())
        {
            Row( modifier = Modifier.verticalScroll(ScrollState(0)))
            {
                if(!newQuestionJustAdded.value && questionIsSaved.value)
                {
                    questionList.forEachIndexed{ index, question ->
                        Button(onClick = {
                            nowQuestion.intValue = index
                             recompose.value = false
                            questionIsSaved.value = false
                            questionList[nowQuestion.intValue].questionId = index
                        },
                            Modifier
                                .height(60.dp)
                                .padding(5.dp)) {
                            Text(text = (index + 1).toString(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp)
                        }
                    }
                }
            }
            if(questionIsSaved.value)
            {
                Button(onClick = {
                    newQuestionJustAdded.value = true
                    if(questionList.isNotEmpty()){
                        nowQuestion.intValue =
                            questionList[questionList.count() - 1].questionId
                    }
                    questionIsSaved.value = false
                },
                    Modifier
                        .height(60.dp)
                        .padding(5.dp)) {
                    Text(text = "Добавить вопрос",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(value = testName.value,
                    label = { Text(text = "Название теста")},
                    placeholder = { Text(text = "Введите название теста")},
                    onValueChange = {
                        testName.value = it
                    },
                    modifier = Modifier
                        .width(250.dp)
                        .padding(30.dp))
                Button(onClick = {
                    if(questionList.isNotEmpty())
                    {
                        if(testName.value != "")
                        {
                            val db = Room.databaseBuilder(
                                applicationContext,
                                AppDatabase::class.java, "database-name"
                            ).allowMainThreadQueries().build()
                            val dao = db.dbDao()

                            if(dao.getTestByLogin(testName.value) == null)
                            {
                               var qList = ""
                                questionList.toMutableStateList().forEachIndexed()
                                {index, item ->
                                    questionList.toMutableStateList()[index].questionId = 0
                                    var ans = dao.insertQuestion(questionList.toMutableStateList()[index])
                                    qList += ans.toString() + "<;>"
                                }
                                qList = qList.dropLast(3)
                                dao.insertTest(Test(testName.value, qList))

                                /*dao.insertTestWithQuestions(QuestionsByTest(
                                    Test(testName.value, ""), questionList))

                                navController.navigate(Screen.AppScreen.withArgs(name.toString()))*/
                            }
                            else { showDialog3.value = true }
                        }
                        else { showDialog2.value = true }
                    }
                    else { showDialog.value = true }
                })
                {
                    Text(text = "Сохранить тест",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp)
                }
            }
            if(showDialog.value)
            {
                Alert(name = "Добавьте вопросы в тест", showDialog.value,
                    onDismiss = {showDialog.value = false})
            }
            if(showDialog2.value)
            {
                Alert(name = "Назовите как-нибудь тест", showDialog2.value,
                    onDismiss = {showDialog2.value = false})
            }
            if(showDialog3.value)
            {
                Alert(name = "Такой тест уже существует", showDialog3.value,
                    onDismiss = {showDialog3.value = false})
            }
            if(questionList.isNotEmpty() && !recompose.value)
            {
                Button(onClick = {
                    questionList.removeAt(nowQuestion.value)
                    nowQuestion.value = questionList.count() - 1
                    questionIsSaved.value = true
                    recompose.value = true
                },
                    Modifier
                        .height(60.dp)
                        .padding(5.dp)) {
                    Text(text = "Удалить этот вопрос",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)
                }
            }
            if(newQuestionJustAdded.value)
            {
                DropdownMenu(expanded = true, onDismissRequest = {}) {
                    Text("Вопрос с ответом строкой",
                        fontSize=18.sp, modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                onClick = {
                                    questionList.add(
                                        Question(
                                            questionList.count(), "",
                                            mutableListOf(), "", 1
                                        )
                                    )
                                    recompose.value = false
                                    newQuestionJustAdded.value = false
                                    if (questionList.isNotEmpty()) {
                                        nowQuestion.intValue =
                                            questionList[questionList.count() - 1].questionId
                                    }
                                }))
                    Text("Вопрос с вариантом ответа",
                        fontSize=18.sp, modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                onClick = {
                                    questionList.add(
                                        Question(
                                            questionList.count(), "",
                                            mutableListOf(), "", 2
                                        )
                                    )
                                    recompose.value = false
                                    newQuestionJustAdded.value = false
                                    if (questionList.isNotEmpty()) {
                                        nowQuestion.intValue =
                                            questionList[questionList.count() - 1].questionId
                                    }

                                }))
                    Text("Вопрос с несколькими вариантами ответа",
                        fontSize=18.sp, modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                onClick = {
                                    questionList.add(
                                        Question(
                                            questionList.count(), "",
                                            mutableListOf(), "", 3
                                        )
                                    )
                                    recompose.value = false
                                    newQuestionJustAdded.value = false
                                    if (questionList.isNotEmpty()) {
                                        nowQuestion.intValue =
                                            questionList[questionList.count() - 1].questionId
                                    }

                                }))
                }
            }
            if(recompose.value)
            {
                Box(modifier = Modifier.fillMaxSize())
            }
            if (questionList.isNotEmpty() &&
                questionList[nowQuestion.intValue].answerType == 1 &&
                !recompose.value) {
                var questionText by remember {
                    mutableStateOf(questionList[nowQuestion.intValue]
                        .questionText) }
                var answerText by remember {
                    mutableStateOf(questionList[nowQuestion.intValue]
                        .trueAnswer) }

                TextField(value = questionText,
                    label = { Text(text = "Ваш вопрос")},
                    placeholder = { Text(text = "Введите ваш вопрос")},
                    onValueChange = {
                        questionText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp))
                TextField(value = answerText,
                    label = { Text(text = "Ответ")},
                    placeholder = { Text(text = "Ответ на ваш вопрос")},
                    onValueChange = {
                        answerText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp))

                Button(onClick = {
                    questionList[nowQuestion.intValue].questionText = questionText
                    questionList[nowQuestion.intValue].trueAnswer = answerText
                    recompose.value = true
                    questionIsSaved.value = true },
                    Modifier
                        .height(60.dp)
                        .padding(5.dp)) {
                    Text(text = "Сохранить вопрос",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)
                } }
            if (questionList.isNotEmpty() &&
                questionList[nowQuestion.intValue].answerType == 2 &&
                !recompose.value) {

                var questionText by remember {
                    mutableStateOf(questionList[
                        nowQuestion.intValue].questionText) }
                val ansTexts = remember { questionList[
                    nowQuestion.intValue]
                    .allAnswers.toMutableStateList()}
                val selectedOption = remember {
                    mutableStateListOf<Boolean>() }

                if(ansTexts.isNotEmpty())
                {
                    questionList[nowQuestion.intValue].
                    trueAnswer.forEach()
                    {t->
                        if(t == '0')
                        { selectedOption.add(false) }
                        else{ selectedOption.add(true) } } }

                TextField(value = questionText,
                    label = { Text(text = "Ваш вопрос")},
                    placeholder = { Text(text = "Введите ваш вопрос")},
                    onValueChange = {
                        questionText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp))

                Column (
                    Modifier
                        .selectableGroup()
                        .verticalScroll(ScrollState(0))){
                    ansTexts.forEachIndexed()
                    {index, item ->
                        Row (verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.horizontalScroll(ScrollState(0))
                        ){
                            RadioButton(selected = ( selectedOption[index]),
                                onClick = {
                                    if(selectedOption.contains(true))
                                    { selectedOption[ selectedOption.
                                    indexOf(true)] = false
                                        selectedOption[index] = true }
                                    else {selectedOption[index] = true}
                                })
                            Text(
                                text = item,
                                textAlign = TextAlign.Center,)
                            TextField(value = item,
                                label = { Text(text = "Вариант")},
                                placeholder = { Text(text = "Вариант ответа")},
                                onValueChange = { ansTexts[index] = it },
                                modifier = Modifier.width(200.dp))
                            Button(onClick = {
                                ansTexts.removeAt(index)
                                selectedOption.removeAt(index)
                                             },
                                Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(5.dp)) {
                                Text(text = "-",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp) } } }
                    Button(onClick = {
                        ansTexts.add("")
                        selectedOption.add(false)
                                     },
                        Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .padding(5.dp)) {
                        Text(text = "+",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp) }
                    Button(onClick = {
                        questionList[nowQuestion.intValue].
                        questionText = questionText
                        questionList[nowQuestion.intValue].
                        allAnswers = ansTexts
                        var t = ""
                        selectedOption.forEach()
                        {b -> if(!b) { t+= "0" }
                            else { t+= "1" } }
                        questionList[nowQuestion.intValue].
                        trueAnswer = t
                        recompose.value = true
                        questionIsSaved.value = true
                                     },
                        Modifier
                            .height(60.dp)
                            .padding(5.dp)) {
                        Text(text = "Сохранить вопрос",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp) } } }
            if (questionList.isNotEmpty() &&
                questionList[nowQuestion.intValue].answerType == 3 &&
                !recompose.value)
            {
                var questionText by remember {
                    mutableStateOf(questionList[nowQuestion.intValue].questionText) }
                val ansTexts = remember { questionList[nowQuestion.intValue].allAnswers.toMutableStateList()}
                val selectedOption = remember { mutableStateListOf<Boolean>() }
                if(ansTexts.isNotEmpty())
                {
                    questionList[nowQuestion.intValue].trueAnswer.forEach()
                    {t->
                        if(t == '0')
                        {
                            selectedOption.add(false)
                        }
                        else{
                            selectedOption.add(true)
                        }
                    }
                }

                TextField(value = questionText,
                    label = { Text(text = "Ваш вопрос")},
                    placeholder = { Text(text = "Введите ваш вопрос")},
                    onValueChange = {
                        questionText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp))

                Column (
                    Modifier
                        .selectableGroup()
                        .verticalScroll(ScrollState(0))){
                    ansTexts.forEachIndexed()
                    {index, item ->
                        Row (verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.horizontalScroll(ScrollState(0))
                        ){
                            Checkbox(checked = selectedOption[index],
                                onCheckedChange = {
                                    if(selectedOption[index])
                                    {
                                        selectedOption[index] = false
                                    }
                                    else{
                                        selectedOption[index] = true
                                    }
                                })
                            Text(
                                text = item,
                                textAlign = TextAlign.Center,
                            )
                            TextField(value = item,
                                label = { Text(text = "Вариант")},
                                placeholder = { Text(text = "Вариант ответа")},
                                onValueChange = {
                                    ansTexts[index] = it
                                },
                                modifier = Modifier
                                    .width(200.dp))

                            Button(onClick = {
                                ansTexts.removeAt(index)
                                selectedOption.removeAt(index)
                            },
                                Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(5.dp)) {
                                Text(text = "-",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp)
                            }

                        }
                    }
                    Button(onClick = {
                        ansTexts.add("")
                        selectedOption.add(false)
                    },
                        Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .padding(5.dp)) {
                        Text(text = "+",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp)
                    }
                    Button(onClick = {
                        questionList[nowQuestion.intValue].questionText = questionText
                        questionList[nowQuestion.intValue].allAnswers = ansTexts
                        var t = ""
                        selectedOption.forEach()
                        {b ->
                            if(!b)
                            {
                                t+= "0"
                            }
                            else
                            {
                                t+= "1"
                            }
                        }
                        questionList[nowQuestion.intValue].trueAnswer = t
                        recompose.value = true
                        questionIsSaved.value = true
                    },
                        Modifier
                            .height(60.dp)
                            .padding(5.dp)) {
                        Text(text = "Сохранить вопрос",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp)
                    }
                }

            }
        }

    }

    private @Composable
    fun NewTestScreen(name: String?, navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    navController.navigate(Screen.CreatingNewTestScreen.withArgs(name.toString()))
                }) {
                Text(
                    text = "Создать новый тест",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    navController.navigate(Screen.TestToRecreateScreen.withArgs(name.toString()))
                }) {
                Text(
                    text = "Изменить тест",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    navController.navigate(Screen.DeleteTestScreen.withArgs(name.toString()))
                }) {
                Text(
                    text = "Удалить тест",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }

    @Composable
    fun LoginScreen(navController: NavController) {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        val dao = db.dbDao()

        if(dao.loadUserByLogin("admin")== null)
        {
            dao.insertUser(User("admin", "admin", "", "", true))
        }

        var loginFieldText by remember { mutableStateOf("") }
        var passwordFieldText by remember { mutableStateOf("") }

        val showDialog = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = loginFieldText,
                label = { Text(text = "Логин")},
                placeholder = { Text(text = "Введите ваш логин")},
                onValueChange = {
                    loginFieldText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )
            TextField(
                value = passwordFieldText,
                label = { Text(text = "Пароль")},
                placeholder = { Text(text = "Введите ваш пароль")},
                onValueChange = {
                    passwordFieldText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    if(loginFieldText != "" && passwordFieldText != ""
                        && dao.loadUserByLogin(loginFieldText) != null) {
                        val user = dao.loadUserByLogin(loginFieldText)

                        if (user != null) {
                            if(passwordFieldText == user.password) {
                                navController.navigate(
                                    Screen.AppScreen
                                        .withArgs(user.firstName.toString()
                                            + " " + user.lastName.toString())
                                        + ":;:" + user.login) }
                            else
                                showDialog.value = true } }
                    else
                    {
                        showDialog.value = true }
                }) {
                Text(
                    text = "Войти",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp) }
            if (showDialog.value)
            {
                Alert(name = "Введите правильные данные", showDialog.value,
                    onDismiss = {showDialog.value = false})
            }

            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                }) {
                Text(
                    text = "Зарегистрироваться",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }
    @Composable
    fun Alert(name: String,
              showDialog: Boolean,
              onDismiss: () -> Unit) {
        if (showDialog) {
            AlertDialog(
                title = {
                    Text("Ошибка")
                },
                text = {
                    Text(text = name)
                },
                onDismissRequest = onDismiss,
                confirmButton = {
                    TextButton(onClick = onDismiss ) {
                        Text("OK")
                    }
                },
                dismissButton = {}
            )
        }
    }
    @Composable
    fun Alert2(name: String,
              showDialog: Boolean,
              onDismiss: () -> Unit) {
        if (showDialog) {
            AlertDialog(
                title = {
                    Text("Удаление")
                },
                text = {
                    Text(text = name)
                },
                onDismissRequest = onDismiss,
                confirmButton = {
                    TextButton(onClick = onDismiss ) {
                        Text("OK")
                    }
                },
                dismissButton = {}
            )
        }
    }

    @Composable
    fun AppScreen(name: String?, navController: NavController) {
        var log : String = ""
        var n :String

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            name?.let {
                n = name.split(":;:")[0]
                log = name.split(":;:")[1]
                Text(
                    text = "Пользователь: $n",
                    fontSize = 30.sp
                )
            }
            Spacer(modifier = Modifier.height(100.dp))

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()
            val dao = db.dbDao()

            if(dao.loadUserByLogin(log)!!.isTeacher)
            {
                Button(modifier = Modifier.padding(5.dp),
                    onClick = { navController.navigate(Screen.NewTestScreen.withArgs(name.toString()))  }) {
                    Text(
                        text = "Создать/Изменить тест",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
                Button(modifier = Modifier.padding(5.dp),
                    onClick = { navController.navigate(Screen.CreateNewUserScreen.withArgs(name.toString())) }) {
                    Text(
                        text = "Добавить нового пользователя",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
                Button(modifier = Modifier.padding(5.dp),
                    onClick = { navController.navigate(Screen.UserForUserResults.withArgs(name.toString())) }) {
                    Text(
                        text = "Результаты учеников",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            }
            Button(modifier = Modifier.padding(5.dp),
                onClick = { navController.navigate(Screen.TestToCompleteScreen.withArgs(name.toString())) }) {
                Text(
                    text = "Пройти тест",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            Button(modifier = Modifier.padding(5.dp),
                onClick = { navController.navigate(Screen.ResultsScreen.withArgs(name.toString())) }) {
                Text(
                    text = "Ваши результаты",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            Button(modifier = Modifier.padding(5.dp),
                onClick = { navController.navigate(Screen.UpdateUserInformationScreen.withArgs(name.toString())) }) {
                Text(
                    text = "Изменить данные аккаунта",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            Button(modifier = Modifier.padding(5.dp),
                onClick = { navController.navigate(Screen.LoginScreen.route)})
            {
                Text(
                    text = "Выход",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }

    @Composable
    fun RegisterScreen(navController: NavController)
    {

        var loginFieldText by remember { mutableStateOf("") }
        var passwordFieldText by remember { mutableStateOf("") }
        var nameFieldText by remember { mutableStateOf("") }
        var surnameFieldText by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false)}

        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            TextField(value = loginFieldText,
                label = { Text(text = "Логин")},
                placeholder = { Text(text = "Введите ваш логин")},
                onValueChange = {
                    loginFieldText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )
            TextField(value = passwordFieldText,
                label = { Text(text = "Пароль")},
                placeholder = { Text(text = "Введите ваш пароль")},
                onValueChange = {
                    passwordFieldText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )
            TextField(value = nameFieldText,
                label = { Text(text = "Имя")},
                placeholder = { Text(text = "Введите ваше имя")},
                onValueChange = {
                    nameFieldText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )
            TextField(value = surnameFieldText,
                label = { Text(text = "Фамилия")},
                placeholder = { Text(text = "Введите вашу фамилию")},
                onValueChange = {
                    surnameFieldText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()
            val dao = db.dbDao()

            Button(modifier = Modifier.padding(5.dp),
                onClick = {
                    if(dao.loadUserByLogin(loginFieldText) == null)
                    {
                        dao.insertUser(User(login = loginFieldText,
                            password = passwordFieldText,
                            firstName = nameFieldText,
                            lastName = surnameFieldText,
                            isTeacher = false)
                        )
                        navController.navigate(Screen.LoginScreen.route)
                    }
                    else { showDialog = true}
                }) {
                Text(text = "Создать аккаунт")
            }
        }
    }

    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
        factory = {
            val density = LocalDensity.current
            val strokeWidthPx = density.run { strokeWidth.toPx() }

            Modifier.drawBehind {
                val width = size.width
                val height = size.height - strokeWidthPx/2

                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = height),
                    end = Offset(x = width , y = height),
                    strokeWidth = strokeWidthPx
                )
            }
        }
    )

}

