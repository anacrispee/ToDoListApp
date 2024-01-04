package com.example.todolistapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolistapp.model.Task
import com.example.todolistapp.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToDoListApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ToDoListApp(){
    Scaffold(
        topBar = { CenterAlignedTopAppBar(
            title = {
                Text(
                    text ="To Do List",
                    color = Color(0xFFF2F1EB)
                )
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color(0xFF88AB8E),

            )
        )},
        content = {
            Column {
                Spacer(modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFF2F1EB)))
                AppContent()
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(){

    var allTasks by rememberSaveable { mutableStateOf(mutableListOf<Task>()) }
    var taskInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Color(0xFFF2F1EB))
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
        ) {
            TextField(
                modifier = Modifier
                    .padding(10.dp),
                textStyle = TextStyle(
                    color = Color(0xFF906652)
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFEEE7DA)),
                label = {
                    Text(
                    color = Color(0xFFccbda1),
                    text = stringResource(R.string.add_task_label)
                    )},
                value = taskInput,
                onValueChange = { taskInput = it.take(30) }
            )
            ButtonStyle (onClick = {
                val newTask = Task(taskInput)
                allTasks.add(newTask)
                taskInput = ""
            },
                btnTxt = R.string.add_task_btn,
                modifier = Modifier
            )
        }
        TaskList(
            taskList = allTasks,
            onRemoveClick = { task ->
                allTasks = allTasks.toMutableList().apply { remove(task) }
            }
            )
    }
}

@Composable
fun TaskList(
    taskList: MutableList<Task>,
    onRemoveClick: (Task) -> Unit,
    modifier: Modifier =  Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ){
        items(taskList) { task ->
            TaskCard(
                task = task,
                onRemoveClick = {
                    onRemoveClick(task)
                }
            )
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onRemoveClick: (Task) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier
            .background(Color(0xFFEEE7DA))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.padding(start = 10.dp),
            text = "$task")
        Spacer(modifier = Modifier.size(20.dp))
        ButtonStyle(
            modifier = Modifier.padding(end = 10.dp),
            onClick = {
            onRemoveClick(task)},
            btnTxt = R.string.remove_task)
    }
}

@Composable
fun ButtonStyle(
    modifier: Modifier,
    onClick: () -> Unit,
    btnTxt: Int
){
    Button(
    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF88AB8E)),
    modifier = Modifier
        .padding(5.dp),
    onClick = onClick
    ){
        Text(text = stringResource(btnTxt))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ToDoListAppTheme {
        ToDoListApp()
    }
}