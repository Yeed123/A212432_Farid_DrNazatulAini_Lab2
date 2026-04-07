package com.example.a212432_farid_drnazatulaini_lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a212432_farid_drnazatulaini_lab2.ui.theme.A212432_Farid_DrNazatulAini_Lab2Theme

data class Job(
    val title: String,
    val company: String,
    val location: String
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A212432_Farid_DrNazatulAini_Lab2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Jobture(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Jobture(modifier: Modifier = Modifier) {

    val jobList = listOf(
        Job("Software Developer", "Google", "Kuala Lumpur"),
        Job("Backend Developer", "Shopee", "Bangi"),
        Job("UI/UX Designer", "Grab", "Kuala Lumpur"),
        Job("Software Engineer", "AirAsia", "Sepang"),
        Job("Intern Developer", "Petronas", "KL"),
        Job("Clerk", "Klinik Far", "Semenyih"),
        Job("Delivery Driver", "FedEx", "Bangsar"),
        Job("Store Keeper", "MajuMart", "Bangi")
    )

    // STATE
    var searchText by remember { mutableStateOf("") }
    var locationText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var filteredJobs by remember { mutableStateOf(jobList) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.jobture),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Surface(
            color = MaterialTheme.colorScheme.background.copy(alpha = 0.10f),
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.jtlogo2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .padding(bottom = 1.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("Job title", fontFamily = FontFamily.Serif) },
                        modifier = Modifier.weight(1.2f)
                    )

                    OutlinedTextField(
                        value = locationText,
                        onValueChange = { locationText = it },
                        label = { Text("Location", fontFamily = FontFamily.Serif) },
                        modifier = Modifier.weight(0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                //  BUTTON WITH ICON + IMPROVED LOGIC
                Button(
                    onClick = {

                        if (searchText.isBlank() || locationText.isBlank()) {
                            resultText = "Please enter job title and location"
                            filteredJobs = jobList
                        } else {
                            //  FILTER LOGIC (Dynamic UI improvement)
                            filteredJobs = jobList.filter {
                                it.title.contains(searchText, ignoreCase = true) &&
                                        it.location.contains(locationText, ignoreCase = true)
                            }

                            resultText = if (filteredJobs.isEmpty()) {
                                "No jobs found for \"$searchText\" in \"$locationText\""
                            } else {
                                "${filteredJobs.size} job(s) found for \"$searchText\" in \"$locationText\""
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC8AF55))
                ) {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Find Jobs", fontFamily = FontFamily.Serif)
                }

                //  DYNAMIC RESULT TEXT
                if (resultText.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = resultText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily.Serif
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // FILTERED JOB LIST
                LazyColumn {
                    items(filteredJobs) { job ->
                        JobCard(job)
                    }
                }
            }
        }
    }
}

@Composable
fun JobCard(job: com.example.a212432_farid_drnazatulaini_lab2.Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = job.title,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = job.company, fontFamily = FontFamily.Serif)

            Text(text = job.location, fontFamily = FontFamily.Serif)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CleanPreview() {
    A212432_Farid_DrNazatulAini_Lab2Theme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Preview Mode (No Image)")
                Spacer(modifier = Modifier.height(10.dp))
                JobCard(Job("Android Dev", "Google", "KL"))
            }
        }
    }
}