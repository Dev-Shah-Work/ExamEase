import { Directive, ElementRef, HostListener, Input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appClickHighlight]'
})
export class ClickHighlightDirective {
  
  constructor(private el:ElementRef,private renderer:Renderer2) { }
  @Input() appClickHighlight: string = 'yellow';
  @HostListener('click') onClick(){
    this.highlight();
  }
  private highlight() {
    this.renderer.setStyle(this.el.nativeElement, 'background-color', this.appClickHighlight);
  }

}
