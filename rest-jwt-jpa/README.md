# API

```text
> POST /api/auth/v1/register

{
  "username": "max",
  "firstName": null,
  "lastName": "max",
  "password": "123"
}
```

```text
> POST /api/auth/v1/login

{
  "username": "max",
  "password": "123"
}
```

```text
> POST /api/auth/v1/changeRole
> Authorization: <JWT>
{
  "username": "max",
  "role": "ADMIN"
}
```

```text
> POST /api/items/v1/add
> Authorization: <JWT>

{
  "name": "Apple",
  "count": 42,
  "note": "Important apple"
}
```

```text
> PUT /api/items/v1/update
> Authorization: <JWT>

{
  "id": 1,
  "name": "Apple",
  "count": 22,
  "note": "Very important apple"
}
```

```text
> GET /api/items/v1/getAll
> Authorization: <JWT>
```

```text
> GET /api/items/v1/getAllByUser
> Authorization: <JWT>
```

```text
> DELETE /api/items/v1/deleteById?itemId=1
> Authorization: <JWT>
```
