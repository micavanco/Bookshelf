import { Pipe, PipeTransform } from '@angular/core';
import { IBook } from "../interfaces";

@Pipe({
  name: 'sortBy'
})
export class SortByPipe implements PipeTransform {

  transform(array: Array<IBook>, args?: any): any {
    if(array) {

      let sortType = args[0];

      if(sortType === 'pages_left')
        array.sort((a: any, b: any) => {
          if(a['pages'] - a['pages_done'] < b['pages'] - b['pages_done']) return -1;
          else if(a['pages'] - a['pages_done'] > b['pages'] - b['pages_done']) return 1;
          else return 0;
        });
      else
        array.sort((a: any, b: any) => {
          if(a[sortType] < b[sortType]) return -1;
          else if(a[sortType] > b[sortType]) return 1;
          else return 0;
        });

      return array;
    }
    return null;
  }

}
