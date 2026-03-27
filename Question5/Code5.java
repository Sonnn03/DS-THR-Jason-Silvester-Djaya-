import java.util.*;

public class Code5 {
    
    // Class Card yang mengimplementasikan Comparable agar mudah di-sort
    static class Card implements Comparable<Card> {
        int value;
        int category;

        public Card(int value, int category) {
            this.value = value;
            this.category = category;
        }

        // Sorting: Kategori terkecil lebih dulu, jika sama maka nilai terkecil
        @Override
        public int compareTo(Card other) {
            if (this.category != other.category) {
                return Integer.compare(this.category, other.category);
            }
            return Integer.compare(this.value, other.value);
        }

        @Override
        public String toString() {
            return value + "," + category;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Menggunakan List of List untuk menghindari warning/error Array Generik di beberapa platform
        List<List<Card>> hands = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            hands.add(new ArrayList<>());
        }
        
        // Membaca input kartu untuk 4 pemain (Sangat kebal terhadap baris kosong/spasi berlebih)
        for (int i = 0; i < 4; ) {
            if (!scanner.hasNextLine()) break;
            String line = scanner.nextLine().trim();
            
            // Abaikan jika ternyata membaca baris kosong yang tidak disengaja
            if (line.isEmpty()) continue;
            
            String[] parts = line.split("\\s+");
            for (String part : parts) {
                // Pastikan yang dibaca benar-benar format "value,category"
                if (part.contains(",")) { 
                    String[] vc = part.split(",");
                    hands.get(i).add(new Card(Integer.parseInt(vc[0]), Integer.parseInt(vc[1])));
                }
            }
            // Sort kartu milik pemain sejak awal
            Collections.sort(hands.get(i));
            
            // Hanya maju ke pemain berikutnya jika baris ini sukses memuat kartu
            i++; 
        }

        // Membaca pemain yang jalan duluan (index 0 - 3)
        int currentPlayer = -1;
        while (scanner.hasNextInt()) {
            currentPlayer = scanner.nextInt() - 1;
            break;
        }
        
        // Jika input gagal dibaca sampai akhir, hentikan program
        if (currentPlayer == -1) {
            scanner.close();
            return; 
        }

        // Stack untuk melacak riwayat permainan (Deque adalah best practice untuk Stack di Java)
        Deque<Card> stack = new ArrayDeque<>();

        // Status putaran (trick) saat ini
        int currentTrickCategory = -1;
        int currentTrickValue = -1;
        int consecutivePasses = 0;
        int winner = -1;

        // Game Loop
        while (true) {
            // Jika 3 orang lain berturut-turut pass, putaran reset
            if (consecutivePasses == 3) {
                currentTrickCategory = -1;
                currentTrickValue = -1;
                consecutivePasses = 0;
            }

            if (currentTrickCategory == -1) {
                // MULAI PUTARAN BARU
                // Ambil kartu paling pertama (otomatis kartu terkecil di kategori terkecil karena di-sort)
                Card toPlay = hands.get(currentPlayer).remove(0);
                stack.push(toPlay);
                
                currentTrickCategory = toPlay.category;
                currentTrickValue = toPlay.value;
                consecutivePasses = 0;

                // Cek apakah pemain menang
                if (hands.get(currentPlayer).isEmpty()) {
                    winner = currentPlayer + 1; // Ubah balik ke index manusia (1-4)
                    break;
                }
            } else {
                // MELANJUTKAN PUTARAN (Cari kartu sah menimpa tumpukan)
                Card toPlay = null;
                int playIdx = -1;
                
                for (int i = 0; i < hands.get(currentPlayer).size(); i++) {
                    Card c = hands.get(currentPlayer).get(i);
                    // Syarat: kategori sama dan nilai LEBIH BESAR
                    if (c.category == currentTrickCategory && c.value > currentTrickValue) {
                        toPlay = c;
                        playIdx = i;
                        break; // Langsung break karena kartu terurut, ini pasti yang paling berhemat!
                    }
                }

                if (toPlay != null) {
                    // Ada kartu yang sah untuk diletakkan
                    hands.get(currentPlayer).remove(playIdx);
                    stack.push(toPlay);
                    
                    currentTrickValue = toPlay.value;
                    consecutivePasses = 0;

                    // Cek apakah pemain menang
                    if (hands.get(currentPlayer).isEmpty()) {
                        winner = currentPlayer + 1;
                        break;
                    }
                } else {
                    // Tidak ada kartu yang sah, pemain pass
                    consecutivePasses++;
                }
            }
            
            // Giliran berpindah ke pemain selanjutnya secara berputar
            currentPlayer = (currentPlayer + 1) % 4;
        }

        // Output hasil sesuai spesifikasi
        System.out.println(winner);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

        scanner.close();
    }
}