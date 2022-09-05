# CommandRequest
#### It allows us to enter commands in your server's CMD.
#### After selling a VIP, Item or Anything, you can use it to quickly deliver the purchased item to the Player.
---

<details><summary>Example Code JavaScript</summary>
  
```js
function opPlayer(username){
  const post = fetch("localhost:8080/command?username=root&password=secret&command=op NoNametxt", {method: "POST"})
    .then(res => res.json());
  console.log(post);
}
//Returns true if Successful
```

</details>

---

<details><summary>Config File</summary>
  
```yaml
# CommandRequest
# Usage:
# It can only be used with the Post method.
# localhost:8080/command?username=root&password=secret&command=op NoNametxt

username: "root"
password: "secret"
url: "/command" # localhost:PORT/command
port: 8080 # localhost:8080
```
  
</details>

---
