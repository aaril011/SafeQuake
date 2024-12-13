import streamlit as st
import pandas as pd
import numpy as np
import tensorflow as tf
from sklearn.preprocessing import StandardScaler
import joblib


# Fungsi untuk memuat model dan scaler yang sudah dilatih
@st.cache_resource
def load_model_and_scaler():
    # Pastikan model dan scaler telah disimpan sebelumnya
    model = tf.keras.models.load_model('model.h5')  # Ubah dengan path model Anda
    scaler = joblib.load('scaler.pkl')  # Ubah dengan path scaler Anda
    return model, scaler

# Fungsi prediksi risiko bencana
def predict_disaster_risk(input_data, model, scaler):
    # Normalisasi input data menggunakan scaler
    input_scaled = scaler.transform(input_data)

    # Prediksi kelas risiko
    prediction = model.predict(input_scaled)
    
    # Menentukan tingkat risiko dengan nilai probabilitas tertinggi
    risk_class = np.argmax(prediction, axis=1)[0]
    risk_map = {0: "rendah", 1: "sedang", 2: "tinggi"}
    risk_scores = prediction[0]  # Skor probabilitas untuk setiap tingkat risiko
    
    # Rekomendasi penanganan
    recommendations = {
        "rendah": """
        Penanganan:
        Tetap tenang dan waspada terhadap informasi terbaru dari pihak berwenang.
        Jaga komunikasi dengan keluarga dan tetangga. Siapkan rencana sederhana untuk menghadapi situasi darurat, seperti lokasi aman terdekat.
        Pastikan Anda mengetahui jalur evakuasi dan lokasi penampungan terdekat.
        Kesiapsiagaan:
        Pantau kondisi cuaca atau aktivitas seismik di sekitar area secara berkala.
        Periksa kondisi rumah atau bangunan untuk memastikan tidak ada kerusakan kecil yang dapat memburuk.
        """,
        
        "sedang": """
        Penanganan:
        Persiapkan perlengkapan darurat seperti tas berisi obat-obatan, makanan, air, dan pakaian.
        Siapkan rencana evakuasi dan tentukan titik berkumpul bagi keluarga.
        Cek jalur komunikasi darurat seperti radio atau aplikasi bencana untuk mendapatkan informasi terbaru.
        Waspadai potensi kerusakan di sekitar bangunan dan infrastruktur.
        Kesiapsiagaan:
        Pastikan kendaraan memiliki bahan bakar yang cukup untuk evakuasi jika diperlukan.
        Latih seluruh anggota keluarga tentang rencana darurat dan pastikan semua orang tahu peran masing-masing.
        Amankan barang-barang berharga dan dokumen penting di tempat yang aman.
        """,
        "tinggi": """
        Penanganan:
        Segera lakukan evakuasi ke tempat yang aman sesuai instruksi dari pihak berwenang.
        Siapkan kebutuhan darurat, seperti air, makanan, pakaian, senter, dan dokumen penting di dalam tas yang mudah dibawa.
        Hindari berada di bangunan atau tempat berisiko tinggi seperti lereng curam atau dekat pantai jika terjadi gempa bumi atau tsunami.
        Ikuti perkembangan informasi dari sumber resmi seperti pemerintah atau lembaga penanggulangan bencana.
        Kesiapsiagaan:
        Pastikan jalur evakuasi bebas dari hambatan dan bisa diakses dengan cepat.
        Kenali tempat pengungsian terdekat dan rencanakan jalur teraman untuk mencapainya.
        Jaga keselamatan keluarga dan pastikan semua orang berada di tempat yang aman sesuai prosedur evakuasi.
        """
        }
    
    predicted_risk = risk_map[risk_class]
    recommendation = recommendations[predicted_risk]
    
    # Menyusun hasil prediksi
    result = {
        "Prediksi Risiko": predicted_risk,
        "Skor Risiko": {
            "rendah": round(risk_scores[0], 2),
            "sedang": round(risk_scores[1], 2),
            "tinggi": round(risk_scores[2], 2)
        },
        "Rekomendasi Penanganan": recommendation
    }
    
    return result

# Judul aplikasi
st.title("Prediksi Risiko Bencana Gempa Bumi")
st.write("Masukkan data lingkungan Anda untuk memprediksi risiko bencana gempa bumi.")

# Input dari pengguna
longitude = st.number_input("Longitude", value=-0.8912, format="%.4f", help="Koordinat longitude lokasi Anda")
latitude = st.number_input("Latitude", value=119.8707, format="%.4f", help="Koordinat latitude lokasi Anda")
elevation = st.number_input("Elevation (m)", value=50, help="Ketinggian lokasi dalam meter")
population_density = st.number_input("Population Density (people/km²)", value=120, help="Kepadatan penduduk per km²")
seismic_activity = st.number_input("Seismic Activity Level", value=5, help="Tingkat aktivitas seismik (1-10)")
infrastructure_density = st.number_input("Infrastructure Density (buildings/km²)", value=1500, help="Kepadatan infrastruktur per km²")
soil_stability = st.number_input("Soil Stability Index", value=0.8, format="%.1f", help="Indeks kestabilan tanah (0-1)")

# Tombol untuk memproses prediksi
if st.button("Prediksi Risiko"):
    # Muat model dan scaler
    model, scaler = load_model_and_scaler()
    
    # Data input dari pengguna
    input_data = np.array([[longitude, latitude, elevation, population_density, seismic_activity, infrastructure_density, soil_stability]])
    
    # Prediksi risiko
    result = predict_disaster_risk(input_data, model, scaler)
    
    # Menampilkan hasil prediksi
    st.subheader("Hasil Prediksi")
    st.write(f"**Prediksi Risiko:** {result['Prediksi Risiko'].capitalize()}")
    st.write(f"**Rekomendasi Penanganan:** {result['Rekomendasi Penanganan']}")
