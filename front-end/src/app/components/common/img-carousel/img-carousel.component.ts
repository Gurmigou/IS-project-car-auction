import { Component } from '@angular/core';

@Component({
  selector: 'app-img-carousel',
  templateUrl: './img-carousel.component.html',
  styleUrls: ['./img-carousel.component.css']
})
export class ImgCarouselComponent {
  images = [
    'https://www.infinitiq50.org/attachments/img_2004-jpg.70113/',
    'https://cs.copart.com/v1/AUTH_svc.pdoc00001/LPP335/0aa9131a219a47c1b5eb85224b8a8353_hrs.jpg',
    'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfGBU3WJ0ei-sTFdkbcFklWMTYWeFHoisx4Q&usqp=CAU',
  ];
  currentImageIndex  = 0;

  selectImage(index: number): void {
    this.currentImageIndex = index;
  }

  nextImage(): void {
    this.currentImageIndex = (this.currentImageIndex + 1) % this.images.length;
  }

  previousImage(): void {
    this.currentImageIndex = (this.currentImageIndex - 1 + this.images.length) % this.images.length;
  }
}
