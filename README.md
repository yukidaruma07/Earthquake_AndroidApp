# 概要
* 地震情報を表示するアプリです。

# コードの一部説明
```java
scheduler.scheduleWithFixedDelay() によって1分ごとに実行
　　　　　↓
repository.fetchEarthquakes()　で地震情報を取得する
　　　　　↓
・nullじゃなかったら：インターフェースのonResultを実行。LiveData#postValueも実行することで、HomeFragmentにラムダ式で書いた、EarthquakeAdapter#updateが実行され、中身が更新される。
・nullだったら：インターフェースのonErrorを実行
```

# 使用ライブラリ・API
* P2P地震情報API： https://www.p2pquake.net/develop/json_api_v2/

# 動作イメージ（2026/7/15時点）
<img width="352" height="502" alt="image" src="https://github.com/user-attachments/assets/b57aaa07-2331-4345-9e52-d9a057f3bee1" />
