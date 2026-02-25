# CoffeeManagement

CoffeeManagement, bir kahve mağazası zinciri için sipariş, ürün ve mağaza yönetimini sağlayan Spring Boot tabanlı bir uygulamadır. Bu proje, mağaza ve ürün yönetimi, sipariş takibi ve gelir raporlaması gibi temel işlevleri içerir.

## Özellikler

- Mağaza ve ürün yönetimi
  - Mağazalara ürün ekleme
  - Ürünlerin fiyat ve stok güncellemeleri
  - Ürünlerin hangi mağazalarda olduğunu listeleme
- Sipariş yönetimi
  - Kullanıcılar için sipariş oluşturma
  - Sipariş ürünlerini takip etme
  - Toplam sipariş tutarının hesaplanması
- Raporlama
  - Tüm mağazalardan elde edilen toplam kazanç
  - Bir mağazaya ait toplam kazanç
  - En çok kazandıran mağaza bilgisi

## Teknolojiler

- Java 17
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- Maven
- H2 veya MySQL veritabanı
- Postman (API testleri için)
