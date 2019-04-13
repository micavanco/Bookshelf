export interface UserData {
  user: [{
    id: bigint,
    username: string,
    password: string,
    name: string,
    surname: string,
    city: string,
    createdAt: string,
    library_id: number
  }]
}
