// full-screen.service.ts
import { Injectable, Renderer2 } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FullScreenService {
  private isFullScreen = false;

  constructor(private renderer: Renderer2) {}

  enterFullScreen() {
    this.isFullScreen = true;
    this.renderer.addClass(document.body, 'full-screen-mode');
    // Additional logic to disable keyboard here
  }

  exitFullScreen() {
    this.isFullScreen = false;
    this.renderer.removeClass(document.body, 'full-screen-mode');
    // Additional logic to enable keyboard here
  }

  isFullScreenMode() {
    return this.isFullScreen;
  }
}
