export interface IBook {
  title: string,
  author: string,
  year: number,
  pages: number,
  pages_done: number,
  language: string,
  publisher: string,
  cover: string
}

export interface IUser {
  user_id: number,
  username: string,
  password: string,
  enabled: boolean,
  authority: string
}
