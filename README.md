# MyMovieCatalogue
Submission pertama dari MADE Dicoding


Untuk menyelesaikan modul Fundamentals, Anda diharuskan men-submit aplikasi yang telah Anda buat sendiri. Aplikasi yang akan Anda buat adalah aplikasi katalog film.
Dengan memanfaatkan materi pada modul Fundamentals dan ditambah dengan API dari themoviedb.org, Anda diharapkan dapat membuat aplikasi yang sesuai dengan persyaratan untuk menyelesaikan submission.



Requirements atau fitur yang harus ada pada aplikasi anda

Halaman untuk mencari film.
Halaman detail untuk menampilkan detail fim yang telah dipilih pada halaman list film.
Tampilan poster dari film.

Contoh aplikasi 

Pencarian film
20170703044910b71279ce06fdee524be385cd650bbde7.png

Hint

Buat account pada www.themoviedb.org kemudian masuk ke account -> dan API untuk mendapatkan API Key
Gunakankanlah API Key(v3 auth)
201611262345443be875e49e3063914ffc2045da373cdd.png
Gunakan url ini untuk mencari data movie

https://api.themoviedb.org/3/search/movie?api_key=<API KEY ANDA>&language=en-US&query=<INPUTAN NAMA MOVIE>

 Gunakan url ini untuk mendapatkan poster movie.

http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg


tw185 pada link di atas merupakan ukuran poster. Tersedia beberapa ukuran yang dapat Anda gunakan w92, w154, w185, w342, w500, w780, dan original. 

5N20rQURev5CNDcMjHVUZhpoCNC.jpg dapat anda dapatkan dari json poster_path dari hasil search movie anda.


Penjelasan mengenai poster dapat dilihat pada halaman ini
https://developers.themoviedb.org/3/configuration/get-api-configuration



Restriction atau Ketentuan dari aplikasi yang di submit

Project dari aplikasi harus menggunakan Android Studio.
Bahasa pemrograman yang digunakan adalah Java. Standar penilaian yang digunakan oleh tim Reviewer adalah dengan menggunakan bahasa Java.
