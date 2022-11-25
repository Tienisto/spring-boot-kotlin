# API

```text
> POST /api/register

{
  "name": "max",
  "password": "123"
}
```

```text
> POST /api/login

{
  "name": "max",
  "password": "123"
}
```

```text
> POST /api/items
> Authorization: <JWT>

{
  "name": "Apple",
  "count": 42,
  "note": "Important apple"
}
```

```text
> PUT /api/items
> Authorization: <JWT>

{
  "id": 1,
  "name": "Apple",
  "count": 22,
  "note": "Very important apple"
}
```

```text
> GET /api/items
> Authorization: <JWT>
```

```text
> DELETE /api/items?itemId=1
> Authorization: <JWT>
```
