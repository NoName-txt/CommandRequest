# CommandRequest
#### It allows us to enter commands in your server's CMD.
#### After selling a VIP, Item or Anything, you can use it to quickly deliver the purchased item to the Player.
#### You can download the plugin from [Spigot](https://www.spigotmc.org/resources/commandrequest.105097/).

---

<details><summary>Example Code JavaScript</summary>
  
```js
function opPlayer(username){
  const post = fetch(`http://localhost:8080/command?username=root&password=secret&command=op ${username}`, {method: "POST"})
    .then(res => res.json());
  console.log(post);
}
opPlayer("NoNametxt");
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
