package com.example.nammahomestay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammahomestay.ui.theme.NammahomeStayTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NammahomeStayTheme {
                AppScreen()
            }
        }
    }
}

val Brown900 = Color(0xFF3E2723)
val Brown700 = Color(0xFF5D4037)
val Orange500 = Color(0xFFFF6B35)
val Teal500 = Color(0xFF00897B)
val Blue700 = Color(0xFF1565C0)
val Purple700 = Color(0xFF6A1B9A)
val Green700 = Color(0xFF2E7D32)
val Cream = Color(0xFFFFF8F0)
val Gold = Color(0xFFFFB300)

@Composable
fun AppScreen() {
    var screen by remember { mutableStateOf("home") }
    AnimatedContent(targetState = screen) { target ->
        when (target) {
            "home"    -> HomeScreen { screen = it }
            "profile" -> ProfileScreen { screen = "home" }
            "menu"    -> MenuScreen { screen = "home" }
            "inquiry" -> InquiryScreen { screen = "home" }
            "guide"   -> GuideScreen { screen = "home" }
        }
    }
}

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Brown900, Brown700, Color(0xFF8D6E63), Cream)
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(50.dp))
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Brush.radialGradient(listOf(Gold, Orange500))),
                contentAlignment = Alignment.Center
            ) {
                Text("🏡", fontSize = 48.sp)
            }
            Spacer(Modifier.height(16.dp))
            Text("Namma HomeStay", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("🌊 Coastal Karnataka Host App", fontSize = 15.sp, color = Color.White.copy(alpha = 0.85f))
            Spacer(Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem("⭐", "4.9", "Rating")
                    StatItem("👥", "48", "Guests")
                    StatItem("🏆", "Top", "Host")
                    StatItem("💰", "₹800", "Per Day")
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Cream)
                .padding(20.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Manage Your Stay", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Brown900)
                Text("✨", fontSize = 22.sp)
            }
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FeatureCard(Modifier.weight(1f), "📸", "Home\nProfile", listOf(Orange500, Color(0xFFFF8F00))) { onNavigate("profile") }
                FeatureCard(Modifier.weight(1f), "🍛", "Daily\nMenu", listOf(Teal500, Color(0xFF00ACC1))) { onNavigate("menu") }
            }
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FeatureCard(Modifier.weight(1f), "📩", "Inquiry\nBox", listOf(Blue700, Color(0xFF1976D2))) { onNavigate("inquiry") }
                FeatureCard(Modifier.weight(1f), "🗺️", "Local\nGuide", listOf(Purple700, Color(0xFF8E24AA))) { onNavigate("guide") }
            }
            Spacer(Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🌟", fontSize = 22.sp)
                        Spacer(Modifier.width(8.dp))
                        Text("Today's Highlights", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Brown900)
                    }
                    Spacer(Modifier.height(12.dp))
                    HighlightRow("🍽️", "Special: Akki Rotti & Fish Curry")
                    HighlightRow("🛏️", "2 rooms available tonight")
                    HighlightRow("📬", "3 new inquiries waiting")
                    HighlightRow("🌤️", "Perfect weather for eco tourism")
                }
            }
            Spacer(Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Brown700)
            ) {
                Text(
                    "🌿 Eco Tourism • 🌊 Coastal Stay • 💚 Farm Fresh",
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun FeatureCard(modifier: Modifier, emoji: String, title: String, colors: List<Color>, onClick: () -> Unit) {
    Card(
        modifier = modifier.height(140.dp).clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(emoji, fontSize = 38.sp)
                Spacer(Modifier.height(8.dp))
                Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun StatItem(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(emoji, fontSize = 20.sp)
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(label, fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
    }
}

@Composable
fun HighlightRow(emoji: String, text: String) {
    Row(modifier = Modifier.padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(emoji, fontSize = 18.sp)
        Spacer(Modifier.width(10.dp))
        Text(text, fontSize = 14.sp, color = Color(0xFF424242))
    }
}

@Composable
fun ScreenHeader(title: String, color: Color, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(color, color.copy(0.7f))))
            .padding(20.dp)
    ) {
        Column {
            Spacer(Modifier.height(30.dp))
            Row(modifier = Modifier.clickable { onBack() }, verticalAlignment = Alignment.CenterVertically) {
                Text("←", fontSize = 24.sp, color = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Back", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun InfoCard(emoji: String, title: String, detail: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(54.dp).clip(RoundedCornerShape(14.dp)).background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) { Text(emoji, fontSize = 28.sp) }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = color)
                Text(detail, fontSize = 13.sp, color = Color(0xFF757575))
            }
        }
    }
}

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Cream).verticalScroll(rememberScrollState())) {
        ScreenHeader("📸 Home Profile", Orange500, onBack)
        Column(modifier = Modifier.padding(20.dp)) {
            InfoCard("🏠", "Room Type", "Clean double room with ceiling fan & fresh linen", Orange500)
            Spacer(Modifier.height(12.dp))
            InfoCard("🚿", "Bathroom", "Attached western toilet with hot water shower", Teal500)
            Spacer(Modifier.height(12.dp))
            InfoCard("🌿", "Surroundings", "Lush coconut farm, peaceful river & nature trails", Green700)
            Spacer(Modifier.height(12.dp))
            InfoCard("✅", "Verified", "Cleanliness, safety & hygiene certified", Teal500)
            Spacer(Modifier.height(12.dp))
            InfoCard("📶", "Amenities", "Free WiFi, home cooked food, free parking", Blue700)
            Spacer(Modifier.height(12.dp))
            InfoCard("💰", "Daily Rate", "₹800 per person including all meals", Gold)
        }
    }
}

@Composable
fun MenuScreen(onBack: () -> Unit) {
    val db = Firebase.firestore
    var breakfast by remember { mutableStateOf("Loading...") }
    var lunch by remember { mutableStateOf("Loading...") }
    var dinner by remember { mutableStateOf("Loading...") }
    var isEditing by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }

    // Load menu from Firebase
    LaunchedEffect(Unit) {
        db.collection("menu").document("today")
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    breakfast = doc.getString("breakfast") ?: "Akki Rotti, Coconut chutney"
                    lunch = doc.getString("lunch") ?: "Fish curry, Steamed rice"
                    dinner = doc.getString("dinner") ?: "Bamboo shoot curry, Chapati"
                } else {
                    breakfast = "Akki Rotti, Coconut chutney, Filter coffee"
                    lunch = "Fish curry, Steamed rice, Papad & Pickle"
                    dinner = "Bamboo shoot curry, Chapati, Dal & Salad"
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize().background(Cream).verticalScroll(rememberScrollState())) {
        ScreenHeader("🍛 Daily Menu", Teal500, onBack)
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = {
                        if (isEditing) {
                            isSaving = true
                            val menuData = hashMapOf(
                                "breakfast" to breakfast,
                                "lunch" to lunch,
                                "dinner" to dinner
                            )
                            db.collection("menu").document("today")
                                .set(menuData)
                                .addOnSuccessListener { isSaving = false }
                        }
                        isEditing = !isEditing
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isEditing) Green700 else Teal500
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        if (isSaving) "💾 Saving..."
                        else if (isEditing) "✅ Save to Firebase"
                        else "✏️ Edit Menu"
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            if (isEditing) {
                EditableField("🌅 Breakfast", breakfast) { breakfast = it }
                Spacer(Modifier.height(12.dp))
                EditableField("☀️ Lunch", lunch) { lunch = it }
                Spacer(Modifier.height(12.dp))
                EditableField("🌙 Dinner", dinner) { dinner = it }
            } else {
                InfoCard("🌅", "Breakfast", breakfast, Orange500)
                Spacer(Modifier.height(12.dp))
                InfoCard("☀️", "Lunch", lunch, Teal500)
                Spacer(Modifier.height(12.dp))
                InfoCard("🌙", "Dinner", dinner, Blue700)
            }
            Spacer(Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Green700),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("💰", fontSize = 36.sp)
                    Text("₹800 per person", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Includes all meals & stay", fontSize = 14.sp, color = Color.White.copy(alpha = 0.85f))
                }
            }
        }
    }
}

@Composable
fun EditableField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, fontWeight = FontWeight.Bold, color = Brown700, fontSize = 15.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Teal500,
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}

@Composable
fun InquiryScreen(onBack: () -> Unit) {
    val db = Firebase.firestore
    var inquiries by remember { mutableStateOf(listOf<Map<String, String>>()) }

    LaunchedEffect(Unit) {
        db.collection("inquiries")
            .get()
            .addOnSuccessListener { result ->
                val list = mutableListOf<Map<String, String>>()
                for (doc in result) {
                    list.add(
                        mapOf(
                            "name" to (doc.getString("name") ?: ""),
                            "message" to (doc.getString("message") ?: ""),
                            "phone" to (doc.getString("phone") ?: ""),
                            "time" to (doc.getString("time") ?: "")
                        )
                    )
                }
                if (list.isEmpty()) {
                    inquiries = listOf(
                        mapOf("name" to "Rahul Sharma", "message" to "Is breakfast included?", "phone" to "9876543210", "time" to "2 hrs ago"),
                        mapOf("name" to "Priya Nair", "message" to "Do you have AC rooms?", "phone" to "9123456780", "time" to "5 hrs ago"),
                        mapOf("name" to "Arjun Mehta", "message" to "How far is Gokarna?", "phone" to "9988776655", "time" to "1 day ago")
                    )
                } else {
                    inquiries = list
                }
            }
    }

    val colors = listOf(Orange500, Teal500, Purple700, Green700, Blue700)

    Column(modifier = Modifier.fillMaxSize().background(Cream).verticalScroll(rememberScrollState())) {
        ScreenHeader("📩 Inquiry Box", Blue700, onBack)
        Column(modifier = Modifier.padding(20.dp)) {
            inquiries.forEachIndexed { index, inquiry ->
                InquiryCard(
                    name = inquiry["name"] ?: "",
                    message = inquiry["message"] ?: "",
                    phone = inquiry["phone"] ?: "",
                    time = inquiry["time"] ?: "",
                    color = colors[index % colors.size]
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun InquiryCard(name: String, message: String, phone: String, time: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(48.dp).clip(CircleShape).background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(name.first().toString(), fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Brown900)
                    Text(time, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(Modifier.height(10.dp))
            Text("\"$message\"", fontSize = 14.sp, color = Color(0xFF424242))
            Spacer(Modifier.height(10.dp))
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = color.copy(0.12f))
            ) {
                Text(
                    "📞 $phone",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    color = color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun GuideScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Cream).verticalScroll(rememberScrollState())) {
        ScreenHeader("🗺️ Local Guide", Purple700, onBack)
        Column(modifier = Modifier.padding(20.dp)) {
            InfoCard("💧", "Jog Falls", "20 km • Most visited waterfall in Karnataka", Blue700)
            Spacer(Modifier.height(12.dp))
            InfoCard("🏖️", "Gokarna Beach", "35 km • Peaceful beach, perfect for sunset", Teal500)
            Spacer(Modifier.height(12.dp))
            InfoCard("🌲", "Dandeli Forest", "40 km • Wildlife safari & river rafting", Green700)
            Spacer(Modifier.height(12.dp))
            InfoCard("🕌", "Murdeshwar Temple", "25 km • Famous Shiva temple by the sea", Orange500)
            Spacer(Modifier.height(12.dp))
            InfoCard("🐘", "Sakrebailu Camp", "45 km • Elephant rides & jungle walk", Purple700)
            Spacer(Modifier.height(12.dp))
            InfoCard("🚣", "Sirsi Waterfall", "30 km • Hidden gem, perfect picnic spot", Teal500)
        }
    }
}