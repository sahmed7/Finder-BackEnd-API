# Finder-BackEnd-API

| HTTP REQUEST | PATH | FUNCTIONALITY |
 ------------ |------| ------------- |
| GET |/api/cities| Lists all cities |
| POST |/api/cities| Create new city |
| GET |/api/cities/{cityId}| Get a single city by id |
| PUT | /api/cities/{cityId}| Update a single city by id |
| DELETE | /api/cities/{cityId} | Delete a single city by id |
| GET | /api/cities/{cityId}/restaurants | Get all restaurants for single city by id |
| POST | /api/cities/{cityId}/restaurants | Create a new restaurant in the given city |
| GET | /api/cities/{cityId}/restaurants/{restaurantId} | List single restaurant in specified city by id |
| PUT | /api/cities/{cityId}/restaurants/{restaurantId} | Update single restaurant in specified city by id |
| DELETE | /api/cities/{cityId}/restaurants/{restaurantID} | Delete single restaurant in specified city by id |
| GET | /api/cities/{cityId}/restaurants/{restaurantId}/menu | List menu items for single restaurant in specified city by id |
| POST | /api/cities/{cityId}/restaurants/{restaurantId}/menu | Create a new menu item in the given restaurant in specified city by id |
| GET | /api/cities/{cityId}/restaurants/{restaurantId}/menu/{menuItemId} | Get a single menu item in given restaurant in specified city by id |
| PUT | /api/cities/{cityId}/restaurants/{restaurantId}/menu/{menuItemId} | Update a menu item in the given restaurant in specified city by id |
| DELETE | /api/cities/{cityId}/restaurants/{restaurantId}/menu/{menuItemId} | Delete single menu item in given restaurant in specified city by id |
| POST | /auth/users/register | Registers a new user |
| POST | /auth/users/login | Logs a user in |
