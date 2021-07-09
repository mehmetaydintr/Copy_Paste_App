# Copy Paste App

<img src="https://user-images.githubusercontent.com/37263322/125088649-4f1f3300-e0d6-11eb-96c2-477cc45caaaf.gif" width="300">

## İçerik

1. [Kullanılan Teknolojiler](https://github.com/mehmetaydintr/Copy_Paste_App/blob/main/README.md#kullan%C4%B1lan-teknolojiler)
2. [Proje Tanımı](https://github.com/mehmetaydintr/Copy_Paste_App/blob/main/README.md#proje-tan%C4%B1m%C4%B1)
3. [Kod Tanımı](https://github.com/mehmetaydintr/Copy_Paste_App/blob/main/README.md#kod-tan%C4%B1m%C4%B1)
4. [Örnek Ekran Görüntüleri](https://github.com/mehmetaydintr/Copy_Paste_App/blob/main/README.md#%C3%B6rnek-ekran-g%C3%B6r%C3%BCnt%C3%BCleri)


## Kullanılan Teknolojiler

+ Android Studio

![Image of Android Studio](https://www.xda-developers.com/files/2017/04/android-studio-logo.png)

+ Java

![Image of Java](https://yazilimamelesi.files.wordpress.com/2013/03/java_logo.jpg)


## Proje Tanımı

**_ClipboardManager_** kütüphanesi kullanarak **Android** tabanlı geliştirilmiş basit bir koyala yapıştır örnek projedir.

Android sisteminde bir yazıyı kopyaladığımızda veya bir görüntünün ekran görüntüsünü aldığımız zaman bu verilerin **pano** adı verilen bir yerde tutulduğunu biliyoruz. Bu uygulamamızda girilen bir metini **pano**'ya kopyalayıp daha sonra diğer sayfamızda **pano**'muz üzerinden yapıştırma örneği yaparak nasıl kullanıldığını göreceğiz.

## Kod Tanımı

+ İlk olarak tasarım ile başlayalım.

Uygulamamızda 2 adet ekranımız olacaktır. İlk önce kullanıcının metin girdiği ve kopyalama işlemini yaptığımız ekranın tasarımını yapalım. Bu ekranımıza bir adet `EditText` ve iki adet `Button` ekliyoruz.

![ekran görüntüsü 1](https://user-images.githubusercontent.com/37263322/125083796-7cb5ad80-e0d1-11eb-9348-d55bae33d02e.PNG)

Daha sonra kopyaladığımız metini ekrana yapıştıracağımız ikinci ekranın tasarımına geçelim. Bu ekranımıza bir adet `TextView` ve bir adet `Button` ekliyoruz.

![ekran görüntüsü 2](https://user-images.githubusercontent.com/37263322/125083974-b1c20000-e0d1-11eb-91a1-fd4d4ed4857a.PNG)

+ Şimdi kodlama kısmına geçebiliriz.

İlk olarak ana ekranda ihtiyacımız olan nesnelerin tanımlamalarını yapalım. `ClipData` kopyalayacağımız nesnenin kendisi oluyor. `ClipboardManager` ise panomuz oluyor. İleride daha detaylı açıklayacağım.

```
private EditText editText;
private Button buttonCopy, buttonGo;

private ClipboardManager kopyalamaPano;
private ClipData clipData;
```

Daha sonra nesnelerin atamalarını yapıp **Button**'ları aktif hale getirelim.

```
editText = findViewById(R.id.editText);
buttonCopy = findViewById(R.id.buttonKopyala);
buttonGo = findViewById(R.id.buttonGit);

kopyalamaPano = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

buttonCopy.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String metin = editText.getText().toString().trim();

        if (!metin.isEmpty()) {
            kopyala(metin);
        } else {
            Snackbar.make(buttonCopy, "Lütfen bir şeyler yazınız...", Snackbar.LENGTH_LONG).show();
        }
    }
});

buttonGo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }
});
```

Şimdi asıl işlemimiz olan **_Kopyalama_** işlemini gerçekleştiren fonksiyonu yazalım. İlk olarak kopyalayacağımız veri türü metin olduğu için `ClipData.newPlainText("text", metin)` yardımıyla `ClipData` nesnemizin text olduğunu belirtiyoruz ve kopyalanacak metini içerisine atıyoruz. Daha sonra panomuzu yönetmemizi sağlayan `ClipboardManager` nesnemizi clipData nesnemiz ile **set** ediyoruz. Böylece clipData nesnemiz panomuza kopyalanmış oluyor.

```
public void kopyala(String metin) {
    clipData = ClipData.newPlainText("text", metin);
    kopyalamaPano.setPrimaryClip(clipData);

    Snackbar.make(buttonCopy, "Panoya kopyalandı...", Snackbar.LENGTH_LONG).show();
}
```

Şimdi kopyaladığımız veriyi yapıştıracağımız diğer ekranımıza geçebiliriz. Aynı şekilde ihtiyacımız olan nesnelerin tanımlamalarını ve atamalarını yapıp **Button**'u aktif hale getirelim.

```
private TextView textView;
private Button buttonPaste;

private ClipboardManager kopyalamaPano;
private ClipData clipData;
```

```
textView = findViewById(R.id.textView);
buttonPaste = findViewById(R.id.buttonYapistir);

kopyalamaPano = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

buttonPaste.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        yapistir();
    }
});
```

Şimdi diğer önemli işlemimiz olan yapıştırma işlemini yaptığımız fonksiyonu yazalım. İlk olarak `ClipboardManager` içerisine **set** ettiğimiz `ClipData` nesnemizi `kopyalamaPano.getPrimaryClip()` fonksiyonu ile çekiyoruz. Daha sonra clipData içerisindeki nesneler arasından en son kopyalanan nesneyi seçmek için `ClipData.Item item = clipData.getItemAt(0);` yapısını kullanıyoruz.

```
public void yapistir(){
    clipData = kopyalamaPano.getPrimaryClip();
    ClipData.Item item = clipData.getItemAt(0);

    String kopyalananYazi = item.getText().toString();

    textView.setText(kopyalananYazi);

    Snackbar.make(buttonPaste, "Yazı yapıştırıldı...", Snackbar.LENGTH_LONG).show();
}
```

## Örnek Ekran Görüntüleri

![Ekran 1](https://user-images.githubusercontent.com/37263322/125088220-e6d05180-e0d5-11eb-9653-fca8179866c3.PNG)
![Mesaj 1](https://user-images.githubusercontent.com/37263322/125088213-e637bb00-e0d5-11eb-8b07-2f93127af211.png)
![Ekran 2](https://user-images.githubusercontent.com/37263322/125088258-ecc63280-e0d5-11eb-8c8b-56706b588f0f.PNG)
![Mesaj 2](https://user-images.githubusercontent.com/37263322/125088255-eb950580-e0d5-11eb-965c-8b75c9c04ffd.png)
