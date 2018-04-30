import { Component, OnInit } from '@angular/core';
import { Photo } from "../model/Photo";
import { AppService } from '../photo-app.service';


@Component({
  selector: 'list-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css'],
  providers: [AppService]
})
export class PhotosComponent implements OnInit {

  private photos: Photo[];
  title = 'Photos App';

    constructor(public appService: AppService) {}

  ngOnInit() {  // when comppnent loading get all photos
    this.getAllPhotos();
  }

   getAllPhotos() {
          
      this.appService.findAll()
      .subscribe(photos => {
        console.log(photos,"res");
        this.photos = photos;        
      },
        err => {
          console.log(err);
       }
       
    );
  
  }

}
