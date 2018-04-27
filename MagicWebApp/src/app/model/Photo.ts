export class Photo {
    id: number;
    createdAt: number ;
    name: string ;
    imageUrl: String ;

    constructor(id: number, createdAt: number, name: string, imageUrl: string){
      this.id = id;
      this.createdAt = createdAt;
      this.name = name;
      this.imageUrl = imageUrl;
    }
  }
  